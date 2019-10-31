$('.btn-notification').click(() => {
    if ($('.notification').hasClass('has-notifications')) {
        $('.btn-notification').toggleClass('btn-notification-active');
        $('.notification-sidebar').toggleClass('notification-sidebar-active');
    }
});

$('.btn-notification-dismiss').click(function() {
    const header = $('meta[name="_csrf_header"]').attr('content');
    const token = $('meta[name="_csrf"]').attr('content');
    $.ajax({
        type: 'DELETE',
        url: window.location.origin + '/notifications/dismiss/' + $(this).parent().parent().parent().parent().parent().attr('id'),
        beforeSend: xhr => {
            xhr.setRequestHeader(header, token);
        }
    });
    $(this).parent().parent().parent().parent().parent().parent().remove();

    const count = $('.notification-box').length;
    if (count <= 0) {
        $('.notification').removeClass('has-notifications');
        $('.btn-notification').removeClass('btn-notification-active');
        $('.notification-sidebar').removeClass('notification-sidebar-active');
    }
});
