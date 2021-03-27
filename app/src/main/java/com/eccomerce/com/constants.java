package com.eccomerce.com;

import com.eccomerce.com.categories.modelcategories;
import com.eccomerce.com.model.catmodel;
import com.eccomerce.com.model.model_ulg;
import com.eccomerce.com.model.modeltimeline;
import com.eccomerce.com.model.modelvip;
import com.eccomerce.com.model.shops;

import java.util.ArrayList;

public class constants {
    public  static  String location="",name="",ln="",shopimg="",shopname="";
    public  static  int posimage=0;
    public static boolean iter=true,iter1=true,iter2=true;
    public static int size=0,size2=0,size1=0,size3=0;
    public static modelvip selected;
    public static model_ulg select;
    public static ArrayList<modeltimeline> modeltimelines=new ArrayList<>();
    public static ArrayList<modelvip> vip=new ArrayList<>();
    public static ArrayList<catmodel> catmodels=new ArrayList<>();
    public static ArrayList<String> magazin=new ArrayList<>();
    public static ArrayList<modelvip> allitem =new ArrayList<>();
    public static ArrayList<modelvip> taze =new ArrayList<>();
    public static ArrayList<modelvip> shopitem =new ArrayList<>();
    public static ArrayList<modelvip> search_item =new ArrayList<>();
    public static ArrayList<modelcategories> categories=new ArrayList<>();
    public static ArrayList<String> idfd=new ArrayList<>(),idfd1=new ArrayList<>(),idfd2=new ArrayList<>();
    public  static ArrayList<shops> shopses=new ArrayList<>();
    public static ArrayList<modelvip> showitem =new ArrayList<>();
    public static  String category="",subcategory="",shopid="",listing="",status="";
    public static  ArrayList<String> followings=new ArrayList<>();
    public static ArrayList<modelvip> likeditems =new ArrayList<>();
    public static ArrayList<model_ulg> likedusluga =new ArrayList<>();
    public static ArrayList<model_ulg> ulgslist=new ArrayList<>();
}
