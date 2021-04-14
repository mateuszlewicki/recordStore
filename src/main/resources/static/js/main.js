var search_box = $(".search_box");
var search_btn = $( "#search-btn" );
var menu_box = $('.menu-main');
var menu_btn = $('.menu-toggle-btn');

function toggleSearch() {
    search_box.toggleClass('active');
    search_btn.toggleClass('active');
}
search_btn.click(function(e) {
    menu_box.removeClass('active');
    menu_btn.removeClass('active');
    e.preventDefault();
    toggleSearch();
});

menu_btn.click(function (e){
    search_box.removeClass('active');
    search_btn.removeClass('active');
    e.preventDefault();
    toggleMenu();
});

function toggleMenu() {
    menu_box.toggleClass('active');
    menu_btn.toggleClass('active');
}

$(function() {
    $('#search').autocomplete({
        source: 'autocomplete',
        minLength: 2
    });
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

$('.delete').click(function () {
    var res = confirm('Confirm action');
    if (!res) return false;
});