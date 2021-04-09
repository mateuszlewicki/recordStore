$('.delete').click(function () {
    var res = confirm('Confirm action');
    if (!res) return false;
});

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $("#image").attr("src", e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}

$("#data").change(function () {
    readURL(this);
})

$(function() {
    $('#search').autocomplete({
        source: 'autocomplete',
        minLength: 2
    });
});

$(document).ready(function() {
    $('#format').select2();

    $('#artists_multiple').select2({
        minimumInputLength: 2,
        placeholder: 'Search artist',
        ajax: {
            url: "/artist-name",
            dataType: 'json',
            delay: 250,
            processResults(data) {
                return {
                    results: $.map(data, function (item) {
                        return {
                            text: item.name,
                            id: item.id,
                        }
                    })
                }
            },
            cache: true
        }
    });

    $('#label_single').select2({
        minimumInputLength: 2,
        placeholder: 'Search label',
        ajax: {
            url: "/label-title",
            dataType: 'json',
            delay: 250,
            processResults(data) {
                return {
                    results: $.map(data, function (item) {
                        return {
                            text: item.title,
                            id: item.id,
                        }
                    })
                }
            },
            cache: true
        }
    });

    $('#genres_multiple').select2({
        minimumInputLength: 2,
        placeholder: 'Search genre',
        ajax: {
            url: "/genre-title",
            dataType: 'json',
            delay: 250,
            processResults(data) {
                return {
                    results: $.map(data, function (item) {
                        return {
                            text: item.title,
                            id: item.id,
                        }
                    })
                }
            },
            cache: true
        }
    });
});

$('.dynamic-track-rows').on('click', 'button[data-dynamic-rows-url]', function () {
    let url = $(this).data('dynamic-rows-url');
    let formData = $('form').serializeArray();
    let param = {};
    param["name"] = $(this).attr('name');
    param["value"] = $(this).val();
    formData.push(param);
    $('#dynamicTrackRows').load(url, formData);
});

$('.dynamic-video-rows').on('click', 'button[data-dynamic-rows-url]', function () {
    let url = $(this).data('dynamic-rows-url');
    let formData = $('form').serializeArray();
    let param = {};
    param["name"] = $(this).attr('name');
    param["value"] = $(this).val();
    formData.push(param);
    $('#dynamicVideoRows').load(url, formData);
});