var Nucleus = {
    isScrollBottom: function (element) {
        var maxScrollPosition = element[0].scrollHeight - element.height();
        var scrollPosition = element.scrollTop();
        return (maxScrollPosition == scrollPosition);
    }
};


