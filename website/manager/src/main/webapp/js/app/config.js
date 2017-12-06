/**
 * Created by domea on 17-6-12.
 */
define({
    packages: [],
    urlArgs: "bust=" + (new Date()).getTime(),
    baseUrl: 'js/',
    paths: {
        "jquery18":"./libs/jquery/dist/jquery-1.8.2.min",
        "jquery": './libs/jquery/dist/jquery',
        "bootstrap": './libs/bootstrap/bootstrap',
        'domReady': './libs/domReady/domReady',
        'vue':'./libs/vue/dist/vue',
        'vueRouter':'./libs/vue-router/vue-router',
        'vueResource':'./libs/vue-resource/vue-resource',
        'vuex':'./libs/vuex/vuex',
        'lodash':'./libs/lodash/dist/lodash',
        'text':'./libs/text/text',
        'supersized':'./libs/supersized/supersized.3.2.7',
        'dhtmlx':'./libs/dhtmlxSuite_v51_std/codebase/dhtmlx'
    },
    map: {
        '*': {
            'css': './libs/require-css/css',
            'less':'./libs/require-less/less'
        }
    },
    shim: {
        'dhtmlx':{
            deps:[
                'css!./libs/dhtmlxSuite_v51_std/skins/terrace/dhtmlx',
                'css!./libs/dhtmlxSuite_v51_std/codebase/dhtmlx'
            ]
        },
        'supersized':{
            deps:[
                'jquery18'
            ]
        },
        'bootstrap': {
            deps: [
                'jquery',
                'css!./libs/bootstrap/bootstrap',
                'css!./libs/bootstrap/bootstrap-theme'
            ]
        },
        'vueRouter':{
            deps:[
                'vue'
            ]
        },
        'vueResource':{
            deps:[
                'vue'
            ]
        },
        'vuex':{
            deps:[
                'vue'
            ]
        }
    }
});