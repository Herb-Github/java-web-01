(function($) {
  'use strict';

  $(function() {
    var $fullText = $('.admin-fullText');
    $('#admin-fullscreen').on('click', function() {
      $.AMUI.fullscreen.toggle();
    });

    $(document).on($.AMUI.fullscreen.raw.fullscreenchange, function() {
      $fullText.text($.AMUI.fullscreen.isFullscreen ? '退出全屏' : '开启全屏');
    });

    $('#delete-confirm').on('closed.modal.amui', function() {
      $(this).removeData('amui.modal');
    });

    $(".delete-model").on("click", function(){
      var delLink = this;
      $('#delete-confirm').modal({
        onConfirm: function (options) {
          window.location.href = $(delLink).attr("data-href");
        },
        onCancel: function () {
        }
      });
    });
  });
})(jQuery);
