define(['./config'],function () {
    var _createLayout=function(mainLayout){
        var layout_1 = mainLayout.attachLayout('3J');

        var cell_1 = layout_1.cells('a');
        var cell_2 = layout_1.cells('b');
        var cell_3 = layout_1.cells('c');
        cell_1.setHeight('160');
        cell_1.hideHeader();
        cell_1.fixSize(0,1);
        var str = [
            { type:"block" , name:"form_block_1", list:[
                { type:"input" , name:"form_input_1", label:"Input"  },
                { type:"input" , name:"form_input_2", label:"Input"  },
                { type:"newcolumn"   },
                { type:"input" , name:"form_input_4", label:"Input"  },
                { type:"input" , name:"form_input_5", label:"Input"  }
            ]  },
            { type:"block" , name:"form_block_2", list:[
                { type:"button" , name:"form_button_4", value:"Button"  },
                { type:"newcolumn"   },
                { type:"button" , name:"form_button_6", value:"Button"  }
            ]  }
        ];
        var form_1 = cell_1.attachForm(str);



        cell_2.setWidth('240');
        cell_2.fixSize(1,0);

        var str = [
            { type:"block" , name:"form_block_3", list:[
                { type:"input" , name:"form_input_6", label:"Input"  },
                { type:"input" , name:"form_input_7", label:"Input"  },
                { type:"input" , name:"form_input_8", label:"Input"  },
                { type:"input" , name:"form_input_9", label:"Input"  },
                { type:"input" , name:"form_input_10", label:"Input"  },
                { type:"block" , name:"form_block_4", list:[
                    { type:"button" , name:"form_button_2", value:"Button"  },
                    { type:"newcolumn"   },
                    { type:"button" , name:"form_button_3", value:"Button"  }
                ]  }
            ]  }
        ];
        var form_2 = cell_2.attachForm(str);
    };
    return {
        createLayout:_createLayout
    };
});