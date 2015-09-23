package com.email.ckeditor;

import java.util.ArrayList;
import java.util.List;

import com.ckeditor.CKEditorConfig;
import com.ckeditor.EventHandler;
import com.ckeditor.GlobalEventHandler;



public class Config {
	
	public static CKEditorConfig createConfig() {
		
		CKEditorConfig config = new CKEditorConfig();
//		List<List<String>> list = new ArrayList<List<String>>();
//		
//		//create font bar
//		List<String> source = new ArrayList<String>();
//		source.add("Source");
//		
//		//create tools
//		List<String> tools = new ArrayList<String>();
//		tools.add("Maximize");
//		
//		//create clip board bar
//		List<String> clipBoard = new ArrayList<String>();
//		clipBoard.add("Cut");
//		clipBoard.add("Copy");
//		clipBoard.add("Paste");
//		clipBoard.add("-");
//		clipBoard.add("Undo");
//		clipBoard.add("Redo");
//		
//		//create edit bar
//		List<String> font = new ArrayList<String>();
//		font.add("Bold");
//		font.add("Italic");
//		font.add("Underline");
//		font.add("Strike");
//		font.add("-");
//		font.add("RemoveFormat");
//		
//		//create paragraph
//		List<String> paragraph = new ArrayList<String>();
//		paragraph.add("NumberedList");
//		paragraph.add("BulletedList");
//		
//		//list of toolbar
//		list.add(clipBoard);
//		list.add(font);
//		list.add(paragraph);
//		list.add(source);
//		list.add(tools);
//		
//		
//		config.addConfigValue("toolbar", list);
		config.addConfigValue("width","500");
		
		//System.out.println(list);
		
		return config;
	}
 
	public static EventHandler createEventHandlers() {
		EventHandler handler = new EventHandler();
		handler.addEventHandler("instanceReady","function (ev) { alert(\"Loaded: \" + ev.editor.name); }");
		return handler;
	}
 
	public static GlobalEventHandler createGlobalEventHandlers() {
		GlobalEventHandler handler = new GlobalEventHandler();
		handler.addEventHandler("dialogDefinition","function (ev) {  alert(\"Loading dialog window: \" + ev.data.name); }");
		return handler;
	}
}
