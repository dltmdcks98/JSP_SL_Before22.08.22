package com.aca.web0810.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.aca.web0810.model.BoardManager;

/*웹 기반이 아닌 독립 실행형 기반인 GUI모드로 등록폼을 정의*/
public class FormWin extends JFrame {
	JButton bt;// has a 관계 : 객체가 다른 객체를 멤버로 보유한 관계
	JTextField t_title, t_writer;
	JTextArea area;
	BoardManager boardManager;
	
	public FormWin() {
		
		t_title = new JTextField(17);
		t_writer = new JTextField(17);
		area = new JTextArea(10,23);//row,col
		area.setBackground(Color.yellow);
		bt = new JButton("등록");
		boardManager = new BoardManager();
		//레이아웃 스타일 명시
		this.setLayout(new FlowLayout());//일렬로 배치되는 레이아웃
		add(t_title);
		add(t_writer);
		add(area);
		add(bt);
		
		setSize(300,400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);//윈도우창을 닫을때 프로그램도 같이 종료 
	
		//버튼과 리스너 연결
		//내부 익명 클래스 ->java파일을 더 생성하지말고 일회성인 코드들은 내부에 넣는거
		bt.addActionListener(new ActionListener() {
			//클래스 안에 클래스 -> 내부 익명클래스 => java를 개발자가 파일로 만들었다는 것은 재사용성을 염두하고 정의하는 것 
			//굳이 .java까지 만들 필요없는 1회성 코드들이 있을때 (주로 이벤트 연결) 이름없는 내부 클래스로 간단히 처리할 수 있다. 
			//주의)
			public void actionPerformed(ActionEvent e) {
				regist();
			}
		});
	}
	//중립적으로 정의된  BoardManager를 이용해서 오라클에 글 넣기
	public void regist() {
		String title = t_title.getText();
		String writer = t_writer.getText();
		String content = area.getText();
		boardManager.insert(title,writer,content);
	}
	public static void main(String[] args) {
		new FormWin();
		
	}
}
