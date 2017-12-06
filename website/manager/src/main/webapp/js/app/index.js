'use strict';
(function (root) {
    require(["./config"], function (config) {
        requirejs.config(config);
        require(["jquery18","supersized",'domReady!'],function () {
            var getRootPath_dc = function () {
                var pathName = window.location.pathname.substring(1);
                var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
                if (webName == "") {
                    return window.location.protocol + '//' + window.location.host;
                }
                else {
                    return window.location.protocol + '//' + window.location.host + '/' + webName;
                }
            };
            jQuery(function($){
                $.supersized({
                    // Functionality
                    slide_interval     : 4000,    // Length between transitions
                    transition         : 1,    // 0-None, 1-Fade, 2-Slide Top, 3-Slide Right, 4-Slide Bottom, 5-Slide Left, 6-Carousel Right, 7-Carousel Left
                    transition_speed   : 1000,    // Speed of transition
                    performance        : 1,    // 0-Normal, 1-Hybrid speed/quality, 2-Optimizes image quality, 3-Optimizes transition speed // (Only works for Firefox/IE, not Webkit)

                    // Size & Position
                    min_width          : 0,    // Min width allowed (in pixels)
                    min_height         : 0,    // Min height allowed (in pixels)
                    vertical_center    : 1,    // Vertically center background
                    horizontal_center  : 1,    // Horizontally center background
                    fit_always         : 0,    // Image will never exceed browser width or height (Ignores min. dimensions)
                    fit_portrait       : 1,    // Portrait images will not exceed browser height
                    fit_landscape      : 0,    // Landscape images will not exceed browser width

                    // Components
                    slide_links        : 'blank',    // Individual links for each slide (Options: false, 'num', 'name', 'blank')
                    slides             : [    // Slideshow Images
                        {image : 'img/index/backgrounds/1.jpg'},
                        {image : 'img/index/backgrounds/2.jpg'},
                        {image : 'img/index/backgrounds/3.jpg'}
                    ]
                });
            });
            jQuery(document).ready(function() {
                $('.page-container form').attr("action",getRootPath_dc()+"/login.do");

                $('.page-container form').submit(function(){
                    var username = $(this).find('.username').val();
                    var password = $(this).find('.password').val();
                    var clientId = $(this).find('.clientId').val();
                    if(clientId == '') {
                        $(this).find('.error').fadeOut('fast', function(){
                            $(this).css('top', '27px');
                        });
                        $(this).find('.error').fadeIn('fast', function(){
                            $(this).parent().find('.username').focus();
                        });
                        return false;
                    }
                    if(username == '') {
                        $(this).find('.error').fadeOut('fast', function(){
                            $(this).css('top', '96px');
                        });
                        $(this).find('.error').fadeIn('fast', function(){
                            $(this).parent().find('.password').focus();
                        });
                        return false;
                    }
                    if(password == '') {
                        $(this).find('.error').fadeOut('fast', function(){
                            $(this).css('top', '165px');
                        });
                        $(this).find('.error').fadeIn('fast', function(){
                            $(this).parent().find('.clientId').focus();
                        });
                        return false;
                    }
                });
                $('.page-container form .username, .page-container form .password,.page-container form .clientId').keyup(function(){
                    $(this).parent().find('.error').fadeOut('fast');
                });

                $('.Bluebutton').click(function () {
                    window.location.href="registor.html";
                    //self.location='jb51.htm';
                    //window.navigator="registor.html";
                });
            });
        });
    });
})(this);