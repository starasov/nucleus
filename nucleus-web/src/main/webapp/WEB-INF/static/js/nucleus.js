var Nucleus = {
    ready: function () {
        this.fitScreen();
        this.initializeScroller();
        this.listenWindowResize();
        this.registerEntriesListener();
    },

    fitScreen: function () {
        var contentElement = $('#content');

        var windowHeight = $(window).height();
        var expectedHeight = windowHeight - contentElement.offset().top - 20;

        contentElement.css({'height': expectedHeight + 'px'});
    },

    initializeScroller: function () {
        var scrollPane = $('.scroll-pane');

//        TODO: probably useless
//        var width = scrollPane.width();
//        scrollPane.css({'width': (width + 18) + 'px'});

        scrollPane.jScrollPane({
            contentWidth: '0px', // hides horizontal scroll
            hideFocus: true, // hides yellow border when focused
            mouseWheelSpeed: 50 // speeds up scrolling speed, so it's not so annoooooying
        });
    },

    listenWindowResize: function () {
        $(window).resize(function () {
            Nucleus.fitScreen();
            Nucleus.initializeScroller();
        })
    },

    registerEntriesListener: function () {
        $('.feed-entry').click(function () {
            $(this).find('.feed-entry-long').toggle();
            Nucleus.initializeScroller();
        });
    }
};

$(function () {
    Nucleus.ready();
});


