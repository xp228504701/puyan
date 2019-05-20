(function($) {
    var target = null;
    var template = null;
    var lock = false;
    var cur_last = 0;
    var variables = {'last': 0};
    var settings = {
        'pageSize': '10',
        'address': 'index.php',
        'format': 'json',
        'template': '.single_item',
        'trigger': '.get_more',
        'scroll': 'false',
        'offset': '100',
        'data':{},
        'spinner_code': '<div class="async_loading"></div>',
        'nomore_message':'鎴戜篃鏄湁搴曠嚎鐨勶紒',
        'callback': '',
    };

    var methods = {
        init: function(options) {
            if (typeof(options.callback) !== 'function')
            {
                settings.callback = '';
            }
            return this.each(function() {
                if (options) {
                    $.extend(settings, options);
                }
                template = $(this).children(settings.template).wrap('<div/>').parent();
                template.css('display', 'none');
                if ($("div.more_loader_spinner").length == 0)
                {
                    $(this).append('<div class="more_loader_spinner">' + settings.spinner_code + '</div>');
                }
                else
                {
                    $('div.more_loader_spinner').html(settings.spinner_code).css("width","30px");
                }
                template.remove();
                target = $(this);
                if (settings.scroll == 'false') {
                    $(this).find(settings.trigger).bind('click.more', methods.get_data);
                    $(this).more('get_data');
                } else {
                    if ($(this).height() <= $(this).attr('scrollHeight')) {
                        target.more('get_data', settings.pageSize * 2);
                    }
                    $(this).bind('scroll.more', methods.check_scroll);
                }
            })

        },
        check_scroll: function() {
            if ((target.scrollTop() + target.height() + parseInt(settings.offset)) >= target.attr('scrollHeight') && lock == false) {
                target.more('get_data');
            }
        },
        debug: function() {
            var debug_string = '';
            $.each(variables, function(k, v) {
                debug_string += k + ' : ' + v + '\n';
            });
            alert(debug_string);
        },
        remove: function() {
            target.children(settings.trigger).unbind('.more');
            target.unbind('.more');
            target.children(settings.trigger).hide();

        },
        add_elements: function(data) {
            var root = target;
            var counter = data.count;
            var list = data.list;
            if (list)
            {
                $(list).each(function(key, value) {
                    var t = template;
                    $.each(this, function(key, value) {
                       if (t.find('.' + key)) t.find('.' + key).html(value);
                    });
                    if (settings.scroll == 'true') {
                        root.children('.more_loader_spinner').before(t.html())
                    } else {
                        root.children(settings.trigger).before(t.html())
                    }
                    root.children(settings.template + ':last').attr('id', 'more_element_' + ((variables.last++) + 1));
                })
            }
            else
            {
                methods.remove();
            }
            if (counter < settings.pageSize){
                methods.remove();
                target.children('.more_loader_spinner').html("<div class='jiazai'><em></em><span>"+settings.nomore_message+"</span><em></em></div>").css('width','100%');
            }
        },
        get_data: function() {
            var ile;
            lock = true;
            target.children(".more_loader_spinner").css('display', 'block');
            $(settings.trigger).css('display', 'none');
            if (typeof(arguments[0]) == 'number') ile = arguments[0];
            else {
                ile = settings.pageSize;
            }
            if(variables.last >= cur_last){
                var postdata = settings.data;
                postdata['last'] = variables.last;
                postdata['pageSize'] = ile;
                $.post(settings.address, postdata, function(data){
                    var res = $.parseJSON(base.decode(data.output));
                    $(settings.trigger).css('display', 'block');
                    methods.add_elements(res);
                    if (typeof settings.callback ===  'function')
                    {
                        settings.callback(res);
                    }
                    lock = false;
                }, settings.format);
                cur_last  = cur_last+10;
            }

        }
    };
    $.fn.more = function(method) {
        variables.last =0;
        cur_last = 0;
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method == 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else $.error('Method ' + method + ' does not exist!');

    };
    $(document).ready(function(){
        $(window).on('scroll', function () {
            if ($(window).scrollTop() > ($(document).height() - $(window).height()) -60 ){
                $('.get_more').click();
            }
        });
    });

})(jQuery);