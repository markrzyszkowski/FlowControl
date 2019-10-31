$('#add').submit(function() {
    const hours = parseInt($('#hours').val());
    const overtime = parseInt($('#overtime').val());
    if (overtime > hours) {
        $('#overtime').addClass('is-invalid');
        $('#overTimeError').attr('class', 'invalid-feedback');
        return false;
    }

    return true;
});
