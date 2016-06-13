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
			System.out.println("=====电话本=====");
			System.out.println("1.新增联系人");
			System.out.println("2.修改联系人");
			System.out.println("3.删除联系人");
			System.out.println("4.查看所有联系人");
			System.out.println("5.查询指定联系人");
			System.out.println("6.退出");
			System.out.println("==============");
			System.out.print("请选择：");
			String input = sc.next();
			
			if(input.equals("1")){
				//新增联系人
				addUser();
			}else if(input.equals("2")){
				//修改联系人
				updateUser();
			}else if(input.equals("3")){
				//删除联系人
				deleteUser();
			}else if(input.equals("4")){
				//查看所有
				showAll();
			}else if(input.equals("5")){
				//查询指定联系人
				find();
			}else if(input.equals("6")){
				save();
				System.out.println("【退出成功】");
				break;
			}else {
				System.out.println("【操作错误，请重选】");
			}
		}
	}
	
	//新增联系人
	public void addUser(){
		Contact con = new Contact(
				getStr("请输入姓名："), 
				getStr("请输入电话："), 
				getStr("请输入性别："), 
				getStr("请输入生日："));
		if(users.keySet().contains(con.getName())){
			if(getStr("该姓名已存在，请重新输入（Y/N）：").equals("Y")){
				users.put(con.getName(), con);
				System.out.println("【新增成功】");
			}		
		}else {
			users.put(con.getName(), con);
			System.out.println("【新增成功】");
		}
	}
	
	//修改联系人
	public void updateUser(){
		String name = getStr("请输入要修改的姓名：");
		
		boolean flag = false;
		Contact con = users.get(name);
		for (int i = 0; i < users.size(); i++) {
			if(con!=null && con.getName().equals(name)){
				con.setName(getStr("姓名修改为："));
				con.setPhone(getStr("电话修改为："));
				con.setSex(getStr("性别修改为："));
				con.setBirthday(getStr("生日修改为："));
				flag = true;
			}
		}
		if(flag){
			System.out.println("【修改成功】");
		}else {
			System.out.println("【修改失败】");
		}
	}
	
	//删除联系人
	public void deleteUser(){
		String name = getStr("所要删除的姓名：");
		boolean flag = false;
		Contact con = users.get(name);
		for (int i = 0; i < users.size(); i++) {
			if(con!=null && con.getName().equals(name)){
				users.remove(name);
				flag = true;
			}
		}
		if(flag){
			System.out.println("【删除成功】");
		}else {
			System.out.println("【删除失败】");
		}
	}
	
	//查看所有
	public void showAll(){
		System.out.println("姓名\t电话\t\t性别\t生日");
		
		for (Contact con : users.values()) {
			show(con);
		}
	}
	
	//查询指定联系人
	public void find(){
		String str = getStr("查询条件：");
		System.out.println("姓名\t电话\t\t性别\t生日");
		
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