package com.zz.startup.controller.interceptor;

import com.zz.startup.security.ShiroDbRealm;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

public class LogRequestBodyInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(LogRequestBodyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Subject subject = SecurityUtils.getSubject();
        Object object = subject.getPrincipal();
        if (object == null) {
            return true;
        }

        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) object;
        String userName = user.getUsername();
        ResettableStreamHttpServletRequest wrappedRequest = new ResettableStreamHttpServletRequest(request);
        String body = IOUtils.toString(wrappedRequest.getReader());
        logger.info("user:{}, request: queryURI:{}, body:{}", userName, wrappedRequest.getRequestURI(), body);

        wrappedRequest.resetInputStream();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv)
            throws Exception {
        ResettableStreamHttpServletResponse wrappedResponse = new ResettableStreamHttpServletResponse(response);
        try {
            wrappedResponse.flushBuffer();
        } finally {
            byte[] copy = wrappedResponse.getCopy();
            logger.info("request: queryURI:{}, response body:{}", request.getRequestURI(), new String(copy));
        }
    }

    private static class ResettableStreamHttpServletRequest extends HttpServletRequestWrapper {
        private byte[] rawData;
        private HttpServletRequest request;
        private ResettableServletInputStream servletInputStream;

        public ResettableStreamHttpServletRequest(HttpServletRequest request) {
            super(request);
            this.request = request;
            this.servletInputStream = new ResettableServletInputStream();
        }

        public void resetInputStream() {
            servletInputStream.stream = new ByteArrayInputStream(rawData);
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            if (rawData == null) {
                rawData = IOUtils.toByteArray(this.request.getReader());
                servletInputStream.stream = new ByteArrayInputStream(rawData);
            }
            return servletInputStream;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            if (rawData == null) {
                rawData = IOUtils.toByteArray(this.request.getReader());
                servletInputStream.stream = new ByteArrayInputStream(rawData);
            }
            return new BufferedReader(new InputStreamReader(servletInputStream));
        }


        private class ResettableServletInputStream extends ServletInputStream {
            private InputStream stream;

            @Override
            public int read() throws IOException {
                return stream.read();
            }
        }
    }

    private static class ResettableStreamHttpServletResponse extends HttpServletResponseWrapper {
        private PrintWriter writer;
        private ResponseServletOutputStream copier;
        private ServletOutputStream outputStream;

        public ResettableStreamHttpServletResponse(HttpServletResponse response) {
            super(response);
        }

        public ServletOutputStream getOutputStream() throws IOException {
            if (writer != null) {
                throw new IllegalStateException("getWriter() has already been called on this response.");
            }

            if (outputStream == null) {
                outputStream = getResponse().getOutputStream();
                copier = new ResponseServletOutputStream(outputStream);
            }

            return copier;
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            if (outputStream != null) {
                throw new IllegalStateException("getOutputStream() has already been called on this response.");
            }

            if (writer == null) {
                copier = new ResponseServletOutputStream(getResponse().getOutputStream());
                writer = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()), true);
            }

            return writer;
        }

        @Override
        public void flushBuffer() throws IOException {
            if (writer != null) {
                writer.flush();
            } else if (outputStream != null) {
                copier.flush();
            }
        }

        public byte[] getCopy() {
            if (copier != null) {
                return copier.getCopy();
            } else {
                return new byte[0];
            }
        }

        private class ResponseServletOutputStream extends ServletOutputStream {

            private OutputStream outputStream;
            private ByteArrayOutputStream copy;

            public ResponseServletOutputStream(ServletOutputStream outputStream) {
                this.outputStream = outputStream;
                this.copy = new ByteArrayOutputStream(1024);
            }

            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
                copy.write(b);
            }

            public byte[] getCopy() {
                return copy.toByteArray();
            }
        }
    }
}
