package hu.lagire.difference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Lagire on 2018. 01. 18..
 */

public class SixButtonMode extends AppCompatActivity {

    Button diffs[] = new Button[7], start, diffsanim[] = new Button[12], enablehint;
    TextView time, point, level, hint[]=new TextView[8], enableplus;
    int nums[] = new int[7], inttime=60, pointnum=100, pointnum2, pointshopnum, levelnumber, randomlevel, hints=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sixbuttonmode);
        diffs[0] = (Button) findViewById(R.id.difbut1);
        diffs[1] = (Button) findViewById(R.id.difbut2);
        diffs[2] = (Button) findViewById(R.id.difbut3);
        diffs[3] = (Button) findViewById(R.id.difbut4);
        diffs[4] = (Button) findViewById(R.id.difbut5);
        diffs[5] = (Button) findViewById(R.id.difbut6);
        diffs[6] = (Button) findViewById(R.id.centralbutton);
        hint[0] = (TextView) findViewById(R.id.sixdifbut1hint);
        hint[1] = (TextView) findViewById(R.id.sixdifbut2hint);
        hint[2] = (TextView) findViewById(R.id.sixdifbut3hint);
        hint[3] = (TextView) findViewById(R.id.sixdifbut4hint);
        hint[4] = (TextView) findViewById(R.id.sixdifbut5hint);
        hint[5] = (TextView) findViewById(R.id.sixdifbut6hint);
        diffsanim[0] = (Button) findViewById(R.id.difbut1green);
        diffsanim[1] = (Button) findViewById(R.id.difbut2green);
        diffsanim[2] = (Button) findViewById(R.id.difbut3green);
        diffsanim[3] = (Button) findViewById(R.id.difbut4green);
        diffsanim[4] = (Button) findViewById(R.id.difbut5green);
        diffsanim[5] = (Button) findViewById(R.id.difbut6green);
        diffsanim[6] = (Button) findViewById(R.id.difbut1red);
        diffsanim[7] = (Button) findViewById(R.id.difbut2red);
        diffsanim[8] = (Button) findViewById(R.id.difbut3red);
        diffsanim[9] = (Button) findViewById(R.id.difbut4red);
        diffsanim[10] = (Button) findViewById(R.id.difbut5red);
        diffsanim[11] = (Button) findViewById(R.id.difbut6red);
        enablehint = (Button) findViewById(R.id.enablehint);
        start = (Button) findViewById(R.id.centralstartbutton);
        time = (TextView) findViewById(R.id.timecounter);
        point = (TextView) findViewById(R.id.pointcounter);
        level = (TextView) findViewById(R.id.leveltext);
        enableplus = (TextView) findViewById(R.id.enableplus);
        SharedPreferences sharedpref = getSharedPreferences("gameinfo.txt", Context.MODE_PRIVATE);
        levelnumber = sharedpref.getInt("sixlevel", 0);
        level.setText("LEVEL "+(levelnumber+1));
        pointshopnum = sharedpref.getInt("point", 0);
        pointnum = pointnum+(20*sharedpref.getInt("sixlevel", 0));
        pointnum2 = pointnum;
        point.setText(""+pointnum);
        randomlevel = sharedpref.getInt("currentlevel", 1)+20;

    }

    public void enablehint(View V) {
        if (hints==0) {
            enableplus.setText("Hints are enabled.");
            hints=1;
        } else {
            enableplus.setText("Hints are disabled. JUST LIKE YOU! YOU FUCKING PIECE OF SHIT!");
            hints=0;
        }
    }

    public void call(View v) {
        v.setAlpha(1f);
        AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0f);
        animation1.setDuration(1500);
        animation1.setFillAfter(true);
        v.startAnimation(animation1);
        v.setVisibility(View.GONE);
    }

    public void callhint(int a) {
        if (nums[6]>nums[a]) {
            hint[a].setText(""+(nums[6]-nums[a]));
        } else {
            hint[a].setText(""+(nums[a]-nums[6]));
        }
    }

    public void newrandom(int i) {
        Random r = new Random();
        int rand = (r.nextInt(randomlevel));
        nums[i]=rand;
        diffs[i].setText(""+rand);
    }

    public void check(int a) {
        int maxdif = 0;
        for (int j=0; j<6; j++) {
            if (maxdif < nums[j] - nums[6]) {
                maxdif = nums[j] - nums[6];
            } else {
                if (maxdif < nums[6] - nums[j]) {
                    maxdif = nums[6] - nums[j];
                }
            }
        }
        if ((maxdif==nums[a]-nums[6]) || (maxdif==nums[6]-nums[a])) {
            pointnum=pointnum-maxdif;
            call(diffsanim[a]);
            if (pointnum<=pointnum2) {
                SharedPreferences sharedpref = getSharedPreferences("gameinfo.txt", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpref.edit();
                if (sharedpref.getInt("purchase", 0)==1) {
                    editor.putInt("point", pointshopnum+maxdif*2);
                    editor.apply();
                    pointshopnum=pointshopnum+maxdif*2;
                } else {
                    editor.putInt("point", pointshopnum+maxdif);
                    editor.apply();
                    pointshopnum=pointshopnum+maxdif;
                }
            }
        } else if ((nums[a]-nums[6])>(nums[6]-nums[a])) {
            pointnum=pointnum+nums[a]-nums[6];
            diffsanim[a+6].setVisibility(View.VISIBLE);
            call(diffsanim[a+6]);
        } else {
            pointnum=pointnum+nums[6]-nums[a];
            diffsanim[a+6].setVisibility(View.VISIBLE);
            call(diffsanim[a+6]);
        }
        if (pointnum<=0) {
            for (int i=0; i<=5; i++) {
                diffs[i].setEnabled(false);
            }
            for (int i=0; i<=6; i++) {
                call(diffs[i]);
            }
            call(time);
            call(point);
            call(level);
            for (int i=0; i<=5; i++) {
                call(hint[i]);
            }
            findViewById(R.id.winrow).setVisibility(View.VISIBLE);
            AlphaAnimation animation1 = new AlphaAnimation(0f, 1f);
            animation1.setDuration(3000);
            animation1.setFillAfter(true);
            findViewById(R.id.winrow).startAnimation(animation1);
            SharedPreferences sharedpref = getSharedPreferences("gameinfo.txt", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpref.edit();
            editor.putInt("sixlevel", sharedpref.getInt("fourlevel", 0)+1);
            if (hints==1) {
                editor.putInt("hintnumber", sharedpref.getInt("hintnumber", 3)-1);
                editor.apply();
            }
            editor.apply();
        }
        for (int i=0; i<=6; i++) {
            newrandom(i);
        }
        if (hints==1) {
            for (int i=0; i<=5; i++) {
                callhint(i);
            }
        }
        point.setText(""+pointnum);
    }

    public void startgame(View V) {
        enablehint.setVisibility(View.GONE);
        enablehint.setEnabled(false);
        enableplus.setVisibility(View.GONE);
        findViewById(R.id.centralstartbutton).setVisibility(View.GONE);
        for (int i=0; i<=6; i++) {
            diffs[i].setVisibility(View.VISIBLE);
        }
        findViewById(R.id.centralbutton).setVisibility(View.VISIBLE);
        time.setVisibility(View.VISIBLE);
        for (int i=0; i<=6; i++) {
            newrandom(i);
        }
        if (hints==1) {
            for (int i=0; i<=5; i++) {
                callhint(i);
                hint[i].setVisibility(View.VISIBLE);
            }
        }
        new CountDownTimer(inttime*1000, 1000){
            public void onTick(long millisUntilFinished){
                inttime--;
                time.setText(""+(inttime+1));
                if (pointnum<=0) {
                    cancel();
                }
            }
            public  void onFinish(){
                for (int i=0; i<=5; i++) {
                    call(hint[i]);
                }
                for (int i=0; i<=6; i++) {
                    call(diffs[i]);
                }
                for (int i=0; i<=5; i++) {
                    diffs[i].setEnabled(false);
                }
                call(time);
                call(point);
                call(level);
                findViewById(R.id.failrow1).setVisibility(View.VISIBLE);
                AlphaAnimation animation1 = new AlphaAnimation(0f, 1f);
                animation1.setDuration(3000);
                animation1.setFillAfter(true);
                findViewById(R.id.failrow1).startAnimation(animation1);
                if (hints==1) {
                    SharedPreferences sharedpref = getSharedPreferences("gameinfo.txt", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.putInt("hintnumber", sharedpref.getInt("hintnumber", 3)-1);
                    editor.apply();
                }
            }
        }.start();
    }

    public void diffbut1(View V) {
        check(0);
    }

    public void diffbut2(View V) {
        check(1);
    }

    public void diffbut3(View V) {
        check(2);
    }

    public void diffbut4(View V) {
        check(3);
    }

    public void diffbut5(View V) {
        check(4);
    }

    public void diffbut6(View V) {
        check(5);
    }

    public void overtap(View V){
        onBackPressed();
    }
}