package hu.lagire.difference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;



/**
 * Created by Lagire on 2018. 01. 22..
 */

public class MenuActivity extends AppCompatActivity{

    ImageView start, shop, modifier, tutorial, sixbutts, fourbutts, eightbutts, randomgenerator, buyhint, buynextbutton;
    int startup=0, shopup=0, modifierup=0, pointshopnum, randomcosttimer, randomcoshint;
    TextView shoppoint, timertext[]=new TextView[3], hinttext[]=new TextView[2], buynextbuttonrow[]=new TextView[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menudesign);
        start = (ImageView) findViewById(R.id.start);
        shop = (ImageView) findViewById(R.id.shop);
        modifier = (ImageView) findViewById(R.id.modifier);
        tutorial = (ImageView) findViewById(R.id.tutorialnew);
        sixbutts = (ImageView) findViewById(R.id.sixbutton);
        fourbutts = (ImageView) findViewById(R.id.fourbutton);
        eightbutts = (ImageView) findViewById(R.id.eightbutton);
        shoppoint = (TextView) findViewById(R.id.shoppoint);
        timertext[0] = (TextView) findViewById(R.id.timertextrow1);
        timertext[1] = (TextView) findViewById(R.id.timertextrow2);
        timertext[2] = (TextView) findViewById(R.id.timertextrow3);
        hinttext[0] = (TextView) findViewById(R.id.hintrow1);
        hinttext[1] = (TextView) findViewById(R.id.hintrow2);
        randomgenerator = (ImageView) findViewById(R.id.randomgenerator);
        buyhint = (ImageView) findViewById(R.id.buyhint);
        buynextbutton = (ImageView) findViewById(R.id.buynextbutton);
        buynextbuttonrow[0] = (TextView) findViewById(R.id.buynextbuttonrow1);
        buynextbuttonrow[1] = (TextView) findViewById(R.id.buynextbuttonrow2);
    }

    public void startbut(View V) {
        startup=1;
        SharedPreferences sharedpref = getSharedPreferences("gameinfo.txt", Context.MODE_PRIVATE);
        tutorial.setVisibility(View.VISIBLE);
        fourbutts.setVisibility(View.VISIBLE);
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(300);
        animation.setFillAfter(true);
        if (sharedpref.getInt("sixmode", 0)==1) {
            sixbutts.setVisibility(View.VISIBLE);
            sixbutts.startAnimation(animation);
        }
        if (sharedpref.getInt("eightmode", 0)==1) {
            eightbutts.setVisibility(View.VISIBLE);
            eightbutts.startAnimation(animation);
        }
        animationleft(start, shop, modifier, 20);
        tutorial.startAnimation(animation);
        fourbutts.startAnimation(animation);
        tutorial.bringToFront();
        fourbutts.bringToFront();
        sixbutts.bringToFront();
        eightbutts.bringToFront();
        findViewById(R.id.tutorialnew).setEnabled(true);
        findViewById(R.id.sixbutton).setEnabled(true);
        findViewById(R.id.fourbutton).setEnabled(true);
        findViewById(R.id.eightbutton).setEnabled(true);
        shoppoint.setText("");
        for (int i=0; i<=2; i++) {
            timertext[i].setText("");
        }
        for (int i=0; i<=1; i++) {
            hinttext[i].setText("");
            buynextbuttonrow[i].setText("");
        }
    }

    public void shopbut(View V) {
        shopup=1;
        SharedPreferences sharedpref = getSharedPreferences("gameinfo.txt", Context.MODE_PRIVATE);
        pointshopnum = sharedpref.getInt("point", Integer.parseInt("0"));
        randomcosttimer = sharedpref.getInt("currentlevel", 1)*sharedpref.getInt("currentlevel", 1)*70;
        randomcoshint=(sharedpref.getInt("fourlevel", 1)+sharedpref.getInt("sixlevel", 1)+sharedpref.getInt("eightlevel", 1))*50+(sharedpref.getInt("hintnumber", 0)*100);
        shoppoint.setText(""+pointshopnum);
        buynextbutton.setVisibility(View.VISIBLE);
        animationleft(shop, start, modifier, 20);
        timertext[0].setText("Level: "+sharedpref.getInt("currentlevel", 1));
        timertext[1].setText("Cost: "+randomcosttimer);
        timertext[2].setText("Random: "+(sharedpref.getInt("currentlevel", 1)+19));
        hinttext[0].setText("Number: "+sharedpref.getInt("hintnumber", 3));
        hinttext[1].setText("Cost: "+randomcoshint);
        buynextbuttonrow[0].setVisibility(View.VISIBLE);
        buynextbuttonrow[0].setText("New mode");
        if (sharedpref.getInt("sixmode", 0)==0) {
            buynextbutton.setBackgroundResource(R.drawable.sixicon);
            buynextbuttonrow[1].setText("Cost: "+6000);
        }
        if ((sharedpref.getInt("sixmode", 0)==1) && (sharedpref.getInt("eightmode", 0)!=1)) {
            buynextbutton.setBackgroundResource(R.drawable.eighticon);
            buynextbuttonrow[1].setText("Cost: "+8000);
        }
        if (sharedpref.getInt("sixmode", 0)==1 && sharedpref.getInt("eightmode", 0)==1) {
            buynextbuttonrow[1].setText("Bought out.");
        }
        buynextbuttonrow[1].setVisibility(View.VISIBLE);
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(300);
        animation.setFillAfter(true);
        shoppoint.startAnimation(animation);
        randomgenerator.startAnimation(animation);
        buyhint.startAnimation(animation);
        buynextbutton.startAnimation(animation);
        for (int i=0; i<=2; i++) {
            timertext[i].startAnimation(animation);
        }
        for (int i=0; i<=1; i++) {
            hinttext[i].startAnimation(animation);
            buynextbuttonrow[i].startAnimation(animation);
        }
        randomgenerator.bringToFront();
        buyhint.bringToFront();
        buynextbutton.bringToFront();
        if (pointshopnum<randomcosttimer) {
            randomgenerator.setAlpha(0.5f);
            randomgenerator.setEnabled(false);
        } else {
            randomgenerator.setAlpha(1f);
            randomgenerator.setEnabled(true);
        }
        if (pointshopnum<randomcoshint) {
            buyhint.setAlpha(0.5f);
            buyhint.setEnabled(false);
        } else {
            buyhint.setAlpha(1f);
            buyhint.setEnabled(true);
        }
        if (sharedpref.getInt("sixmode", 0)==0 && pointshopnum<6000) {
            buynextbutton.setBackgroundResource(R.drawable.sixicon);
            buynextbutton.setAlpha(0.5f);
            buynextbutton.setEnabled(false);
        } else if (sharedpref.getInt("eightmode", 0)==0 && sharedpref.getInt("sixmode", 0)==1 && pointshopnum<8000) {
            buynextbutton.setBackgroundResource(R.drawable.eighticon);
            buynextbutton.setAlpha(0.5f);
            buynextbutton.setEnabled(false);
        } else {
            buynextbutton.setAlpha(1f);
            buynextbutton.setEnabled(true);
        }
    }

    public void modbut(View V) {
        modifierup=1;
        animationleft(modifier, start, shop, 20);
    }

    public void buyrandomplus (View V) {
        if (pointshopnum>randomcosttimer) {
            SharedPreferences sharedpref = getSharedPreferences("gameinfo.txt", Context.MODE_PRIVATE);
            pointshopnum=pointshopnum-randomcosttimer;
            randomcosttimer = sharedpref.getInt("currentlevel", 1)*sharedpref.getInt("currentlevel", 1)*70;
            randomcoshint=(sharedpref.getInt("fourlevel", 1)+sharedpref.getInt("sixlevel", 1)+sharedpref.getInt("eightlevel", 1))*50+(sharedpref.getInt("hintnumber", 0)*100);
            shoppoint.setText(""+pointshopnum);
            SharedPreferences.Editor editor = sharedpref.edit();
            editor.putInt("point", pointshopnum);
            editor.apply();
            int currentlevel = sharedpref.getInt("currentlevel", 1);
            editor.putInt("currentlevel", currentlevel+1);
            editor.apply();
            timertext[0].setText("Level: "+sharedpref.getInt("currentlevel", 1));
            timertext[1].setText("Cost: "+randomcosttimer);
            timertext[2].setText("Number: "+(sharedpref.getInt("currentlevel", 0)+19));
            if (pointshopnum<randomcosttimer) {
                randomgenerator.setAlpha(0.5f);
                randomgenerator.setEnabled(false);
            } else {
                randomgenerator.setAlpha(1f);
                randomgenerator.setEnabled(true);
            }
            if (pointshopnum<randomcoshint) {
                buyhint.setAlpha(0.5f);
                buyhint.setEnabled(false);
            } else {
                buyhint.setAlpha(1f);
                buyhint.setEnabled(true);
            }
            if (sharedpref.getInt("sixmode", 0)==0 && pointshopnum<6000) {
                buynextbutton.setBackgroundResource(R.drawable.sixicon);
                buynextbutton.setAlpha(0.5f);
                buynextbutton.setEnabled(false);
            } else if (sharedpref.getInt("eightmode", 0)==0 && sharedpref.getInt("sixmode", 0)==1 && pointshopnum<8000) {
                buynextbutton.setBackgroundResource(R.drawable.eighticon);
                buynextbutton.setAlpha(0.5f);
                buynextbutton.setEnabled(false);
            } else {
                buynextbutton.setAlpha(1f);
                buynextbutton.setEnabled(true);
            }
        }
    }

    public void buyhint (View V) {
        if (pointshopnum>randomcoshint) {
            SharedPreferences sharedpref = getSharedPreferences("gameinfo.txt", Context.MODE_PRIVATE);
            pointshopnum=pointshopnum-randomcoshint;
            randomcosttimer = sharedpref.getInt("currentlevel", 1)*sharedpref.getInt("currentlevel", 1)*70;
            randomcoshint=(sharedpref.getInt("fourlevel", 1)+sharedpref.getInt("sixlevel", 1)+sharedpref.getInt("eightlevel", 1))*50+(sharedpref.getInt("hintnumber", 0)*100);
            shoppoint.setText(""+pointshopnum);
            hinttext[0].setText("Number: "+sharedpref.getInt("hintnumber", 3));
            hinttext[1].setText("Cost: "+(randomcoshint+sharedpref.getInt("hintnumber", 0)*50));
            SharedPreferences.Editor editor = sharedpref.edit();
            editor.putInt("point", pointshopnum);
            editor.putInt("hintnumber", sharedpref.getInt("hintnumber", 3)+1);
            editor.apply();
            if (pointshopnum<randomcosttimer) {
                randomgenerator.setAlpha(0.5f);
                randomgenerator.setEnabled(false);
            } else {
                randomgenerator.setAlpha(1f);
                randomgenerator.setEnabled(true);
            }
            if (pointshopnum<randomcoshint) {
                buyhint.setAlpha(0.5f);
                buyhint.setEnabled(false);
            } else {
                buyhint.setAlpha(1f);
                buyhint.setEnabled(true);
            }
            if (sharedpref.getInt("sixmode", 0)==0 && pointshopnum<6000) {
                buynextbutton.setBackgroundResource(R.drawable.sixicon);
                buynextbutton.setAlpha(0.5f);
                buynextbutton.setEnabled(false);
            } else if (sharedpref.getInt("eightmode", 0)==0 && sharedpref.getInt("sixmode", 0)==1 && pointshopnum<8000) {
                buynextbutton.setBackgroundResource(R.drawable.eighticon);
                buynextbutton.setAlpha(0.5f);
                buynextbutton.setEnabled(false);
            } else {
                buynextbutton.setAlpha(1f);
                buynextbutton.setEnabled(true);
            }
        }
    }

    public void buynextbutton (View V) {
        SharedPreferences sharedpref = getSharedPreferences("gameinfo.txt", Context.MODE_PRIVATE);
        randomcosttimer = sharedpref.getInt("currentlevel", 1)*sharedpref.getInt("currentlevel", 1)*70;
        randomcoshint=(sharedpref.getInt("fourlevel", 1)+sharedpref.getInt("sixlevel", 1)+sharedpref.getInt("eightlevel", 1))*50+(sharedpref.getInt("hintnumber", 0)*100);
        if (sharedpref.getInt("sixmode", 0)==1) {
            if (pointshopnum>8000) {
                pointshopnum=pointshopnum-8000;
                shoppoint.setText(""+pointshopnum);
                SharedPreferences.Editor editor = sharedpref.edit();
                editor.putInt("eightmode", 1);
                editor.putInt("point", pointshopnum);
                editor.apply();
                buynextbutton.setAlpha(0.5f);
                buynextbutton.setEnabled(false);
                buynextbuttonrow[1].setText("Bought out.");
            }
        }
        if (sharedpref.getInt("sixmode", 0)==0) {
            if (pointshopnum>6000) {
                pointshopnum=pointshopnum-6000;
                shoppoint.setText(""+pointshopnum);
                SharedPreferences.Editor editor = sharedpref.edit();
                editor.putInt("sixmode", 1);
                editor.putInt("point", pointshopnum);
                editor.apply();
                buynextbuttonrow[1].setText("Cost: "+8000);
                buynextbutton.setBackgroundResource(R.drawable.eighticon);
            }
        }
        if (pointshopnum<randomcosttimer) {
            randomgenerator.setAlpha(0.5f);
            randomgenerator.setEnabled(false);
        } else {
            randomgenerator.setAlpha(1f);
            randomgenerator.setEnabled(true);
        }
        if (pointshopnum<randomcoshint) {
            buyhint.setAlpha(0.5f);
            buyhint.setEnabled(false);
        } else {
            buyhint.setAlpha(1f);
            buyhint.setEnabled(true);
        }
    }

    public void tutorial(View V) {
        Intent i = new Intent(this, TutorialMode.class);
        startActivity(i);
    }

    public void fourbutton (View V) {
        Intent i = new Intent(this, FourButtonMode.class);
        startActivity(i);
    }

    public void sixbutton (View V) {
        Intent i = new Intent(this, SixButtonMode.class);
        startActivity(i);
    }

    public void eightbutton (View V) {
        Intent i = new Intent(this, EightButtonMode.class);
        startActivity(i);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(float px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public void animationback(View v, View b, View n, int k) {
        v.setClickable(true);
        b.setClickable(true);
        n.setClickable(true);
        v.animate().x(findViewById(R.id.coordinator).getX()-dpToPx(k)).setDuration(300).start();
        b.animate().x(findViewById(R.id.coordinator).getX()-dpToPx(k)).setDuration(300).start();
        n.animate().x(findViewById(R.id.coordinator).getX()-dpToPx(k)).setDuration(300).start();
        AlphaAnimation animation1 = new AlphaAnimation(0f, 1f);
        animation1.setDuration(300);
        animation1.setFillAfter(true);
        b.startAnimation(animation1);
        n.startAnimation(animation1);
    }

    public void animationleft(View v, View b, View n, int k) {
        v.animate().x(dpToPx(k)).setDuration(300).start();
        b.animate().x(dpToPx(k)).setDuration(300).start();
        n.animate().x(dpToPx(k)).setDuration(300).start();
        AlphaAnimation animation1 = new AlphaAnimation(1f, 0f);
        animation1.setDuration(300);
        animation1.setFillAfter(true);
        b.startAnimation(animation1);
        n.startAnimation(animation1);
        v.setClickable(false);
        b.setClickable(false);
        n.setClickable(false);
    }

    @Override
    public void onBackPressed() {
        if (startup==0 && shopup==0 && modifierup==0) {
            System.exit(0);
        } else if (startup==1){
            SharedPreferences sharedpref = getSharedPreferences("gameinfo.txt", Context.MODE_PRIVATE);
            startup=0;
            AlphaAnimation animation = new AlphaAnimation(1f, 0f);
            animation.setDuration(300);
            animation.setFillAfter(true);
            animationback(start, shop, modifier, 35);
            if (sharedpref.getInt("sixmode", 0)==1) {
                findViewById(R.id.sixbutton).setAnimation(animation);
            }
            if (sharedpref.getInt("eightmode", 0)==1) {
                findViewById(R.id.eightbutton).setAnimation(animation);
            }
            findViewById(R.id.tutorialnew).setAnimation(animation);
            findViewById(R.id.fourbutton).setAnimation(animation);
            findViewById(R.id.tutorialnew).setEnabled(false);
            findViewById(R.id.sixbutton).setEnabled(false);
            findViewById(R.id.fourbutton).setEnabled(false);
            findViewById(R.id.eightbutton).setEnabled(false);
        } else if (shopup==1) {
            shopup=0;
            animationback(shop, start, modifier, 35);
            AlphaAnimation animation1 = new AlphaAnimation(1f, 0f);
            animation1.setDuration(300);
            animation1.setFillAfter(true);
            shoppoint.startAnimation(animation1);
            randomgenerator.startAnimation(animation1);
            buyhint.startAnimation(animation1);
            buynextbutton.startAnimation(animation1);
            for (int i=0; i<=2; i++) {
                timertext[i].startAnimation(animation1);
            }for (int i=0; i<=1; i++) {
                hinttext[i].startAnimation(animation1);
                buynextbuttonrow[i].startAnimation(animation1);
            }
        }else if (modifierup==1) {
            modifierup=0;
            animationback(modifier, start, shop, 35);
        }
        start.bringToFront();
        shop.bringToFront();
        modifier.bringToFront();
    }
}
