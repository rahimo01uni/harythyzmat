package com.eccomerce.com;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;

/**
 * Created by User on 26.07.2018.
 */

public class Dil {
    public static String dili;
    Db db;
    public Handler s;

    public Dil(Context ctx) {
        s = new Handler();

    }

    String[] listItems = new String[2];

    public static String gowy,erbet,kanagatlanarly,oran_gowy,yza="",
            ajayyp,tagamy_bahalndyryn,mynasyp_baha_bermeginizi_hayys,updateimage,
            tassykla,home="",uslugi="",user="",favorite="",best="",New="",All="",timeline=""
    ,ady,bahasy,maglumat,item_kody,cody,magazinlar,hala_haryt,hala_usluga,yeri,yeri_hint,duzgun,okap,addshop
    ,dil_turk,dill,usluga_gos,surat_gos,gözleg,Bizbarada,password,nomer_hint="",usluga_bahalndyryn,
            magazin_ady="",bahalandyrdyn,magazin_hint="",ulnjy_ady="",maglumat2,magazin_goshulyar,bizbilenhabarlash,bizbarada,hemmesini_doldur,magazin_gushuldy,hyzmat_goshudy,hyzmat_gosulyar,ulnjy_hint="",hyzmatyn_ady,hyzmatyn_hint,magazin_gos="",saylanmadyk,yerlesyan_yeri,email_hint,pass_hint,dus_hint,dus_hint_usluga,nomer;


    public void dil(final Context ctx) {
            db = new Db(ctx);
            Log.d("dilic",db.get_dil());
                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                   // mBuilder.setCancelable(false);

                    if (db.get_dil().equals("ru")) {
                            mBuilder.setTitle("Выберите язык");
                            listItems[0] = "Русский язык";
                            listItems[1] = "Туркменский язык";
                            mBuilder.setSingleChoiceItems(listItems, 0, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                    mBuilder.setSingleChoiceItems(listItems, i, this);
                                                    Log.d("icinde", "dfg");
                                                    Log.d("dilname",""+i);
                                                    if (i == 0) {
                                                            dili = "ru";
                                                            db.inser_dil("ru");
                                                    } else {
                                                            dili = "tm";
                                                            db.inser_dil("tm");
                                                    }
                                            }
                                    }
                            );
                            mBuilder.setPositiveButton("Выбрать", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                            dialogInterface.dismiss();
                                            Intent in = ctx.getPackageManager()
                                                    .getLaunchIntentForPackage(ctx.getPackageName());
                                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ctx.startActivity(in);
                                    }
                            });
                    } else {

                            mBuilder.setTitle("Dili Sayla");
                            listItems[0] = "Rus dili";
                            listItems[1] = "Turkmen dili";
                            mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                            mBuilder.setSingleChoiceItems(listItems, i, this);
                                        Log.d("dilname",""+i);
                                            if (i == 0) {
                                                    dili = "ru";
                                                    db.inser_dil("ru");
                                            } else {
                                                    dili = "tm";
                                                    db.inser_dil("tm");
                                            }
                                    }
                            });
                            mBuilder.setPositiveButton("Saýlaň", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                            dialogInterface.dismiss();
                                            Intent in = ctx.getPackageManager()
                                                    .getLaunchIntentForPackage(ctx.getPackageName());
                                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            ctx.startActivity(in);
                                    }
                            });

                    }
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(1, 1, 1));
            }

    public void set_text(Context ctx) {
       db = new Db(ctx);

       Log.d("dili3","dsfdf"+db.get_dil());
        if (db.get_dil().equals("tm")) {
            timeline= "Zaman çizelgesi";
            updateimage= "updateimage";
            home="Baş sahypa";
            user = "Hasabym";
            uslugi="Hyzmatlar";
            favorite = "Halanlarym";
            best = "VIP";
            New = "Täzeler";
            magazinlar = "Dükanlar";
            gowy="Gowy";
            tassykla="Tassyklamak";
            erbet="Erbet";
            oran_gowy="Gaty gowy";
            kanagatlanarly="Kanagatlanarly";
            tagamy_bahalndyryn="Haryda baha ber";
            usluga_bahalndyryn="Hyzmada baha ber";
            ajayyp="Ajaýyp";
            yza="Yza";
            maglumat="Maglumat:";
            maglumat2="Maglumat";
            ady = "Ady:";
            cody = "Harydyñ kody:";
            bahasy = "Bahasy:";
            best = "VIP";
            surat_gos="Surat ýüklemek";
            hala_haryt = "Halanan harytlar";
            hala_usluga = "Halanan hyzmatlar";
            yeri= "Meniñ ýerleşýän ýerim";
            duzgun = "Düzgünnama";
            okap="okap tanyş";
            addshop="Dükan goşmak" ;
            dil_turk = "Türkmen dili";
            dill="Dil";
            usluga_gos="Hyzmat goşmak";
            gözleg="Bärden gözle";
            Bizbarada="Biz barada";
            password="Parol";
            saylanmadyk="Saýlamak";
            yerlesyan_yeri="Ýeri";
            email_hint="Elektron poçtañyzy giriziñ";
            pass_hint="Parol dörediñ";
            dus_hint="▪Dükan barada maglumat \n▪Dostawka barada maglumat \n▪Dükanyñ ýerleşýän ýeri \n▪Başga maglumatlar";
            nomer="Nomer";
            nomer_hint="El-telefon belgiñizi giriziñ";
            magazin_ady="Dükanyñ ady";
            magazin_hint="Dükanyñ adyny giriziñ";
            ulnjy_ady="Ulanyjynyñ ady";
            ulnjy_hint="Ulanyjy adyny giriziñ";
            magazin_gos="Dükan açmak";
//
            hyzmatyn_ady="Hyzmadyñ ady";
            hyzmatyn_hint="Hyzmadyñ adyny giriziñ";
            yeri_hint="Ýerleşýän ýerini giriziñ";
            dus_hint_usluga="▪Hyzmat barada esasy maglumat \n▪Hyzmadyñ görnüşi barada maglumat  \n▪Hyzmadyñ ýerleşýän ýeri barada maglumat \n▪Başgalar";
            hyzmat_goshudy="Hyzmat goşuldy";
            hyzmat_gosulyar="Hyzmat goşulýar";
            magazin_goshulyar="Dükan açylýar";
            magazin_gushuldy="Dükan açyldy";
            hemmesini_doldur="Hemme setirleri dolduryň!";
            bizbarada="Biz barada";
            bizbilenhabarlash="Biz bilen habarlaşmak";
            bahalandyrdyn = "Siz eýýäm bahalandyrdyňyz";
        } else {

            updateimage = "updateimage";
            //russ
            timeline= "Лента новостей";
            home="Домой";
            user = "Профиль";
            uslugi="Услуги";
            favorite = "Любимые";
            best = "VIP";
            New = "Новые";
            magazinlar = "Магазины";
            gowy="Хорошо";
            tassykla="Подтвердить";
            erbet="Плохой";
            oran_gowy="Отлично";
            kanagatlanarly="Удовлетворительное";
            tagamy_bahalndyryn="Оценить товар";
            usluga_bahalndyryn="Оценить услугу";
            ajayyp="Удивительный";
            yza="Отмена";
            maglumat="Описание:";
            maglumat2="Описание";
            ady = "Имя:";
            cody = "Код товара:";
            bahasy = "Цена:";
            best = "VIP";
            surat_gos="Добавить изображение";
            hala_haryt = "Любимые товары";
            hala_usluga = "Любимые услуги";
            yeri= "Мой адрес";
            duzgun = "Правила использования";
            okap="Рекомендуется";
            addshop="Добавить магазин" ;
            dil_turk = "Русский";
            dill="Язык";
            usluga_gos="Добавить услугу";
            gözleg="поиск...";
            Bizbarada="О нас";
            password="Пароль";
            saylanmadyk="Выбрать";
            yerlesyan_yeri="Место";
            email_hint="Введите электронный адрес";
            pass_hint="Задайте пароль";
            dus_hint="▪Информация о магазине \n▪Информация о доставке \n▪Информация о местонахождении магазина \n▪И т.п";
            nomer="Номер";
            nomer_hint="Введите ваш контактный номер";
            magazin_ady="Название магазина";
            magazin_hint="Введите название магазина";
            ulnjy_ady="Имя пользователя";
            ulnjy_hint="Введите имя пользователя";
            magazin_gos="Открыть магазин";
            //
            hyzmatyn_ady="Название услуги";
            hyzmatyn_hint="Придумайте название услуге";
            yeri_hint="Введите место";
            dus_hint_usluga="▪Информация о услуги \n▪Информация об адресе услуги  \n▪Информация о адрес услуги \n▪И другое";
            hyzmat_goshudy="Услуга добавлена";
            hyzmat_gosulyar="Услуга добавляется";
            magazin_goshulyar="Магазин добавляется";
            magazin_gushuldy="Магазин добавлен";
            hemmesini_doldur="Все поля должны быть заполнены!";
            bizbarada="О нас";
            bizbilenhabarlash="Связаться с нами";
            bahalandyrdyn="Вы уже оценили";
        }

    }
}

