'use strict';
(function (root) {
    require(["./config"], function (config) {
        requirejs.config(config);
        require(['jquery','./app/manager/user/main','dhtmlx','domReady!'],function ($,UserLayout) {
            $(document).ready(function () {
                window.dhx4.skin = 'dhx_terrace';
                var mySidebar = new dhtmlXSideBar({
                    parent: document.body,
                    template: 'icons',
                    autohide: '',
                    header: '1',
                    icons_path: "./js/data/icons_terrace/",
                    width: 60
                    // offsets: {
                    //     top: 6,
                    //     left: 12,
                    //     right: 12,
                    //     bottom: 6
                    // }
                });
                mySidebar.attachHeader("header");
                mySidebar.attachFooter("footer");
                mySidebar.loadStruct("./js/data/sidebar.json",function(){
                    UserLayout.createLayout(mySidebar.cells('recent'));
                });
            });
        });
    });
})(this);