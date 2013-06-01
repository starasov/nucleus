var Nucleus = {
    fitScreen: function (selector, preserved) {
        var element = $(selector);
        var windowHeight = $(window).height();
        var expectedHeight = windowHeight - element.offset().top - preserved;
        element.css({'height': expectedHeight + 'px'});
    },

    isScrollBottom: function (element) {
        var maxScrollPosition = element[0].scrollHeight - element.height();
        var scrollPosition = element.scrollTop();
        return (maxScrollPosition == scrollPosition);
    }
};


