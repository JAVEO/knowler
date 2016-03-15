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
};

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
UrlUtils.backendUrl = "http://localhost:9000";

function ArrayUtils() {}
ArrayUtils.getOrElse = function(array, index, other) {
    if (index < array.length) {
        return array[index];
    }
    return other;
};
ArrayUtils.isEmpty = function(array) {
    return array === undefined || array.length === 0;
};
ArrayUtils.isNotEmpty = function(array) {
    return !ArrayUtils.isEmpty(array);
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
GoogleYouTubeUtils.getVideoDuration = function(response) {
    return Math.round(response.detail.target.F.duration);
};

function StringUtils() {}
StringUtils.isEmpty = function(str) {
    return str === undefined || str === "";
};
StringUtils.isNotEmpty = function(str) {
    return !StringUtils.isEmpty(str);
};