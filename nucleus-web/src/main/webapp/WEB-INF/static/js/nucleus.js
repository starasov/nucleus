var Nucleus = {
    init: function (settings) {
        this.settings = settings;
    },

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
        var contentDiv = $('#content');
        $(contentDiv).scroll(function () {
            if (Nucleus.isScrollBottom(contentDiv)) {
//                alert("Bottom!");
            }
        });
    },

    isScrollBottom: function (element) {
        var maxScrollPosition = element[0].scrollHeight - element.height();
        var scrollPosition = element.scrollTop();
        return (maxScrollPosition == scrollPosition);
    },

    listenWindowResize: function () {
        $(window).resize(function () {
            Nucleus.fitScreen();
        })
    },

    registerEntriesListener: function () {
        $('.feed-entry-short').click(function () {
            $(this).parent().find('.feed-entry-long').toggle();

            var unreadCount = $(this).find('.feed-entry-unread').length;
            if (unreadCount > 0) { // to avoid unnecessary server hits if we already read the article
                var that = this;
                $.get(Nucleus.settings.basePath + "/mark-as-read/" + this.id, function () {
                    $(that).find('.feed-entry-unread').removeClass().addClass('feed-entry-read');
                });
            }
        });
    }
};

$(function () {
    Nucleus.ready();
});


