package com.wang.phone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.wang.entity.Contact;

public class PhoneBook {
	private Scanner sc = new Scanner(System.in);
	private Map<String, Contact> users = new HashMap<>();
	public void start(){
		read();
		
		while(true){
			System.out.println("=====�绰��=====");
			System.out.println("1.������ϵ��");
			System.out.println("2.�޸���ϵ��");
			System.out.println("3.ɾ����ϵ��");
			System.out.println("4.�鿴������ϵ��");
			System.out.println("5.��ѯָ����ϵ��");
			System.out.println("6.�˳�");
			System.out.println("==============");
			System.out.print("��ѡ��");
			String input = sc.next();
			
			if(input.equals("1")){
				//������ϵ��
				addUser();
			}else if(input.equals("2")){
				//�޸���ϵ��
				updateUser();
			}else if(input.equals("3")){
				//ɾ����ϵ��
				deleteUser();
			}else if(input.equals("4")){
				//�鿴����
				showAll();
			}else if(input.equals("5")){
				//��ѯָ����ϵ��
				find();
			}else if(input.equals("6")){
				save();
				System.out.println("���˳��ɹ���");
				break;
			}else {
				System.out.println("��������������ѡ��");
			}
		}
	}
	
	//������ϵ��
	public void addUser(){
		Contact con = new Contact(
				getStr("������������"), 
				getStr("������绰��"), 
				getStr("�������Ա�"), 
				getStr("���������գ�"));
		if(users.keySet().contains(con.getName())){
			if(getStr("�������Ѵ��ڣ����������루Y/N����").equals("Y")){
				users.put(con.getName(), con);
				System.out.println("�������ɹ���");
			}		
		}else {
			users.put(con.getName(), con);
			System.out.println("�������ɹ���");
		}
	}
	
	//�޸���ϵ��
	public void updateUser(){
		String name = getStr("������Ҫ�޸ĵ�������");
		
		boolean flag = false;
		Contact con = users.get(name);
		for (int i = 0; i < users.size(); i++) {
			if(con!=null && con.getName().equals(name)){
				con.setName(getStr("�����޸�Ϊ��"));
				con.setPhone(getStr("�绰�޸�Ϊ��"));
				con.setSex(getStr("�Ա��޸�Ϊ��"));
				con.setBirthday(getStr("�����޸�Ϊ��"));
				flag = true;
			}
		}
		if(flag){
			System.out.println("���޸ĳɹ���");
		}else {
			System.out.println("���޸�ʧ�ܡ�");
		}
	}
	
	//ɾ����ϵ��
	public void deleteUser(){
		String name = getStr("��Ҫɾ����������");
		boolean flag = false;
		Contact con = users.get(name);
		for (int i = 0; i < users.size(); i++) {
			if(con!=null && con.getName().equals(name)){
				users.remove(name);
				flag = true;
			}
		}
		if(flag){
			System.out.println("��ɾ���ɹ���");
		}else {
			System.out.println("��ɾ��ʧ�ܡ�");
		}
	}
	
	//�鿴����
	public void showAll(){
		System.out.println("����\t�绰\t\t�Ա�\t����");
		
		for (Contact con : users.values()) {
			show(con);
		}
	}
	
	//��ѯָ����ϵ��
	public void find(){
		String str = getStr("��ѯ������");
		System.out.println("����\t�绰\t\t�Ա�\t����");
		
		for (Contact con : users.values()) {
			if(con!=null&&(con.getName().contains(str)||con.getPhone().contains(str))){
				show(con);
			}
		}
	}
	
	public String getStr(String msg){
		System.out.println(msg);
//		String str = sc.next();
		return sc.next();
	}
	
	public void show(Contact con){
			System.out.print(con.getName());
			System.out.print("\t");
			System.out.print(con.getPhone());
			System.out.print("\t");
			System.out.print(con.getSex());
			System.out.print("\t");
			System.out.print(con.getBirthday());
			System.out.println();
	}
	
	File file = new File("f:/wang.test");
	final String FGF = ",";
	
	public void save(){
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(users);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void read() {
		if(!file.exists()){
			return;
		}
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			users= (HashMap<String, Contact>) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}