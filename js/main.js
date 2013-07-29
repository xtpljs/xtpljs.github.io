(function (xtpl, $){
	"use strict";


	xtpl.mod('xcode', function (code){
		return xtpl.utils.map(code.split('\n'), function (line, i){
			return line
				.replace(/(\|[^$]+)/, function (_, val){
					return val.replace(/&/g, '&amp;').replace(/"/g, '&quot;');
				})
				.replace(/(".*?")/g, '<span class="code-str">$1</span>')
				.replace(/((?:^\s*| > | = ))(\w+)([.\s#[])/g, '$1<span class="code-node">$2</span>$3')
				.replace(/(&\w+\s)/, '<span class="code-decl">$1</span>')
				.replace(/(#\w+)/, '<span class="code-id">$1</span>')
				.replace(/((?:^\s*|>)\.)([.\w-]+)/g, '$1<span class="code-class">$2</span>')
				.replace(/(\s)(x-\w+:)/g, '$1<span class="code-xprop">$2</span>')
				.replace(/(\s)(\w+:)/g, '$1<span class="code-prop">$2</span>')
				.replace(/\[(\w[^=\]]+)/g, '[<span class="code-prop">$1</span>')
				.replace(/\t/g, '&nbsp; &nbsp;')
				.replace(/\s{2}/g, '&nbsp; &nbsp;')
				.replace(/(\/\/[^$]+)/, '<span class="code-comment">$1</span>')
			;
		}).join('\n');
	});


	xtpl.mod('toXML', function (code){
		return xtpl.utils.map(code.split('\n'), function (line){
			return xtpl.xparse(line).toString()
		}).join('\n');
	});



	/** @namespace hljs -- highlight.js */
	if( window.hljs ){
		xtpl.decl('x-highlight', {
			init: function (el, code){
				var type = $.attr(el, 'data-type');

				el.className += ' language-' + (type == 'js' ? 'javascript' : type);
				el.innerHTML = '<pre><code></code></pre>';

				el.firstChild.firstChild.appendChild(document.createTextNode(code));

				hljs.highlightBlock(el);
			},
			update: function (el, code){
				this.init(el, code);
			}
		});
	}


	xtpl.ctrl('main', function (ctx){
		$(document).on('click', 'a', function (evt){
			var hash = evt.currentTarget.hash;
			if( /#!/.test(hash) ){
				evt.preventDefault();
				location.hash = hash;
				window.onhashchange();
			}
		});

		window.onhashchange = function (){
			var hash = location.hash.replace(/^[^!]+!?/, '') || '/';
			var anchor = hash.slice(1, -1).replace(/\//, '-') || 'index';
			var offset = $('a[name="'+anchor+'"]').offset();

			ctx.$set('page', hash);

			// Scroll to anchor
			if( offset ){
				$('html,body').stop(true).animate({ 'scrollTop': offset.top }, 'slow');
			}
		};


		ctx.counter = 0;


		// Load examples
		ctx.examples = (function (){
			var res = {};
			xtpl.utils.each(xtpl.utils.readFile('./examples/splash.xtpl').split('----- '), function (code){
				var name;
				code = code.replace(/^(.*?) -----/, function (_, val){ name = val; return '' });
				res[name] = { xtpl: code.trim() };
			});

			return	res;
		})();


		// Load readme
		ctx.readme = (function (){
			var readme = {};
			xtpl.utils.each(xtpl.utils.readFile('./README.md').split('## '), function (block){
				block = block.trim();

				var pos = block.indexOf("\n");
				var name = block.substr(0, pos);
				var regexp = /```(\w+)([\s\S]*?)```/g, match;

				readme[name] = { name: name };

				while( match = regexp.exec(block) ){
					readme[name][match[1]] = match[2].trim();
				}
			});
			return	readme;
		})();


		ctx.each_nav = [{ href: "#!/", text: "Splash" }, { href: "#!/api/", text: "Docs" }];
		ctx.each_list = ["Alpha", "Beta", "Gamma"];


		// Animation
		ctx.$delay(function (){
			ctx.$set('showH1', true);

			xtpl.utils.each(['hello', 'counter', 'list', 'todos'], function (name, i){
				ctx.$delay(function (){
					ctx.examples[name].show = true;
				}, 200 * (i + 1));
			});
			
			window.onhashchange();
		});

		window.ctx = ctx;
	});
})(xtpl, jQuery);
