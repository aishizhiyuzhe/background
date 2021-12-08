// <script src="../../static/js/jquery-3.2.1.min.js"></script>
// <link rel="stylesheet" href="../../../static/assets/css/my-task.style.min.css">

function menuAll(menus){
    var arrayObj=new Array();
    var temp;
    for(var i=0;i<menus.length;i++){
        temp=menus[i];
        if(temp.parentId==-1){
            arrayObj.push(temp);
            menus.splice(i,1)
            temp.list=menuIds(menus,temp.menuId);
            continue;
        }
    }

    arrayObj.sort(function(a,b){return b['weight']-b['weight']});
    var string='<ul class=\"menu-list flex-grow-1 mt-3\">';
    $.each(arrayObj,function (n,value){

        string+= '<li class="collapsed">' +
                        '<a class="m-link active" data-bs-toggle="collapse" data-bs-target="#dashboard-Components'+n+'" href="#">' +
                        '<i class="icofont-home fs-5"></i> <span>'+value['menuName']+'</span> ' +
                        '<span class="arrow icofont-dotted-down ms-auto text-end fs-5"></span></a>' +
                    '<!-- Menu: Sub menu ul -->' +
                    '<ul class="sub-menu collapse show" id="dashboard-Components'+n+'">';
        if (value.list){
            value.list.sort(function(a,b){return b['weight']-b['weight']});
            $.each(value.list,function (n2,value2){
                string+='<li><a class="ms-link active" href="'+value2['menuUrl']+'"> <span>'+value2['menuName']+'</span></a></li>'
            })
        }

        string+='</ul>' +
            '</li>'
    });
    string+='</ul>'
    return string;
}

function menuIds(menus,menuId){
    var menuList=new Array();
    for (var i=0;i<menus.length;i++){
        if (menus[i].parentId==menuId){
            menuList.push(menus[i]);
            menus.splice(i,1);
            menus[i].list=menuIds(menus,menus[i].menuId);
        };
    }
    return menuList;
}
