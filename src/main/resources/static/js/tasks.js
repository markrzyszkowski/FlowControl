function getUrlParameter(parameter) {
    const pageURL = window.location.search.substring(1);
    const urlVariables = pageURL.split('&');

    for (let i = 0; i < urlVariables.length; i++) {
        const param = urlVariables[i].split('=');
        if (param[0] === parameter) {
            return param[1] === undefined ? false : decodeURIComponent(param[1]);
        }
    }

    return false;
}

function applyCheckboxes() {
    const completed = $('#completed').prop('checked');
    const assignedToMe = $('#assignedToMe').prop('checked');
    window.location.href = `${window.location.origin}/dashboard/tasks?completed=${completed}&assignedToMe=${assignedToMe}`;
}

$('#upload').on('show.bs.modal', function(event) {
    const button = $(event.relatedTarget);
    const id = button.data('id');
    const name = button.data('name');

    const modal = $(this);
    modal.find('.modal-body input[name="id"]').val(id);
    modal.find('.modal-body input[name="name"]').val(name);
});

$('#completed, #assignedToMe').click(() => applyCheckboxes());

$(document).ready(() => {
    $('#completed').prop('checked', getUrlParameter('completed') === 'true');
    $('#assignedToMe').prop('checked', getUrlParameter('assignedToMe') === 'true');
});
