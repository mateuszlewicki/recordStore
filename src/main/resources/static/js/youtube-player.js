var tag = document.createElement('script');
tag.src = "https://www.youtube.com/player_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

var video_ids = $('.playlist').text().split(',');
var player;
function onYouTubePlayerAPIReady() {
    player = new YT.Player('player', {
        height: '240',
        width: '427',
        events: {
            'onReady': onPlayerReady
        }
    });
}
function onPlayerReady(event) {
    event.target.loadPlaylist(video_ids);
}