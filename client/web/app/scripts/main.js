Number.prototype.toHHMMSS = function() {
    var appendZero = function(d) {
        if (d < 10) {
            return "0" + d;
        }
        return d;
    };

    var sec_num = this;
    var hours = Math.floor(sec_num / 3600);
    var minutes = Math.floor((sec_num % 3600) / 60);
    var seconds = sec_num % 60;

    return [hours, minutes, seconds].map(appendZero).join(":");
}

function UrlUtils() {}
UrlUtils.getParam = function(url, param) {
    var vars = url.split(/[?,&]+/);
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] === param) {
            return pair[1];
        }
    }
    return url;
};

function ArrayUtils() {}
ArrayUtils.getOrElse = function(array, index, other) {
    if (index < array.length) {
        return array[index];
    }
    return other;
};

function GoogleYouTubeUtils() {}
GoogleYouTubeUtils.determinePreferredHeight = function() {
    if(window.innerWidth > 1279) {
    	return 480;
    } else if (window.innerWidth > 799) {
    	return 300;
    }
    return 200;
};