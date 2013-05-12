var Nucleus = {
    ready: function () {
        this.fitScreen();
        this.initializeScroller();
        this.listenWindowResize();
    },

    fitScreen: function () {
        var contentElement = $('#content');

        var windowHeight = $(window).height();
        var expectedHeight = windowHeight - contentElement.offset().top - 20;

        contentElement.css({'height': expectedHeight + 'px'});
    },

    initializeScroller: function () {
        var scrollPane = $('.scroll-pane');
        var width = scrollPane.width();

        scrollPane.css({'width': (width + 18) + 'px'});
        scrollPane.jScrollPane();
    },

    listenWindowResize: function () {
        $(window).resize(function () {
            Nucleus.fitScreen();
            Nucleus.initializeScroller();
        })
    }
};

$(function () {
    Nucleus.ready();
});


