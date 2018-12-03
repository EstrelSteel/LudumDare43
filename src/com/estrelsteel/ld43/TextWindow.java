package com.estrelsteel.ld43;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.estrelsteel.engine2.file.GameFile;

public class TextWindow {

	private JTextArea text;
	private JFrame frame;
	private String content;
	
	public TextWindow(JFrame f) {
		text = new JTextArea();
		text.setEditable(false);
		text.setPreferredSize(new Dimension(320, 320));
		text.setSize(320, 320);
		text.setWrapStyleWord(true);
		text.setLineWrap(true);
		text.setMargin(new Insets(20, 20, 20, 20));
		
		frame = new JFrame("");
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(text, BorderLayout.CENTER);
		
		frame.setSize(320, 320);
		frame.pack();
		
		frame.setResizable(true);
		frame.setLocationRelativeTo(f);
		frame.setVisible(true);
		
		content = "";
	}
	
	public String getContent() {
		return content;
	}
	
	public TextWindow updateContent() {
		text.setText(content.trim());
		return this;
	}
	
	public TextWindow clearContent() {
		content = "";
		return this;
	}
	
	public TextWindow addContent(String content) {
		this.content = this.content + content;
		return this;
	}
	
	public TextWindow setContent(String content) {
		clearContent();
		addContent(content);
		return this;
	}
	
	public TextWindow addLineToContent(String content) {
		addContent("\n" + content);
		return this;
	}
	
	public TextWindow loadContent(GameFile file) {
		for(int i = 0; i < file.getLines().size(); i++) {
			addLineToContent(file.getLines().get(i));
		}
		return this;
	}
	
	public TextWindow loadContent(String path) {
		GameFile file = new GameFile(path);
		try {
			file.setLines(file.readFile());
			loadContent(file);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public void exit() {
		frame.dispose();
	}
}
