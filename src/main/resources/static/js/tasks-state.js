$('#states li').on('click', event => {
    const selectedState = $(event.target).html();
    $('#states li').each(function(i) {
        if ($(this).html() === selectedState) {
            $(this).addClass('is-active');
        } else {
            $(this).removeClass('is-active');
        }
    });
    $('#state').val(selectedState);
});
