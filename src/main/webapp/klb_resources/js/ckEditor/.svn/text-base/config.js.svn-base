/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.plugins.addExternal('fmath_formula', 'plugins/fmath_formula/', 'plugin.js','maxlength', 'plugins/maxlength/');
CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here.
	// For the complete reference:
	// http://docs.ckeditor.com/#!/api/CKEDITOR.config

	config.language = "ja";
	config.filebrowserUploadUrl = common.getUri('/ckEditor/fileUpload.do?uploadType=image');
	config.extraPlugins = 'fmath_formula,font,colorbutton,maxlength';
	
	// The toolbar groups arrangement, optimized for two toolbar rows.
	config.toolbar = [
//     	['Templates', 'Styles','Format','Font'],
//    	['HorizontalRule'],
//    	['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
		['FontSize','TextColor'],
		['Bold','Italic','Underline','-','Subscript','Superscript','-','SpecialChar','fmath_formula'],
    	['Image']
//    	['Maximize']
  	];	
	
	// Remove some buttons, provided by the standard plugins, which we don't
	// need to have in the Standard(s) toolbar.
	config.removeButtons = '';

	// See the most common block elements.
	config.format_tags = 'p;h1;h2;h3;pre';

	// Make dialogs simpler.
	config.removeDialogTabs = 'image:advanced;link:advanced';
	
	config.removePlugins = 'elementspath';
	config.resize_enabled = false;
};

CKEDITOR.on('instanceReady', function(ev) {
    // 処理対象タグ
    var tags = ['div',
                'h1','h2','h3','h4','h5','h6',
                'p',
                'ul','ol','li','dl','dt','dd',
                'table','thead','tbody','tfoot','tr','th','td',
                'pre', 'address'];
    
    for (var key in tags) {
        ev.editor.dataProcessor.writer.setRules(tags[key], {
            breakAfterOpen : false
        });
    }
});