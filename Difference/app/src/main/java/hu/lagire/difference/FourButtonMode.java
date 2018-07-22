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
 * Created by Lagire on 2018. 01. 24..
 */

public class FourButtonMode extends AppCompatActivity {

    Button diffs[] = new Button[5], start, diffsanim[] = new Button[8], enablehint;
    TextView time, point, level, hint[]=new TextView[4], enableplus;
    int nums[] = new int[5], inttime=60, pointnum=100, pointnum2, pointshopnum, fourlevelnumber, randomlevel, hints=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fourbuttonmode);
        diffs[0] = (Button) findViewById(R.id.fourdifbut1);
        diffs[1] = (Button) findViewById(R.id.fourdifbut2);
        diffs[2] = (Button) findViewById(R.id.fourdifbut3);
        diffs[3] = (Button) findViewById(R.id.fourdifbut4);
        diffs[4] = (Button) findViewById(R.id.fourcentralbutton);
        diffsanim[0] = (Button) findViewById(R.id.fourdifbut1green);
        diffsanim[1] = (Button) findViewById(R.id.fourdifbut2green);
        diffsanim[2] = (Button) findViewById(R.id.fourdifbut3green);
        diffsanim[3] = (Button) findViewById(R.id.fourdifbut4green);
        diffsanim[4] = (Button) findViewById(R.id.fourdifbut1red);
        diffsanim[5] = (Button) findViewById(R.id.fourdifbut2red);
        diffsanim[6] = (Button) findViewById(R.id.fourdifbut3red);
        diffsanim[7] = (Button) findViewById(R.id.fourdifbut4red);
        hint[0] = (TextView) findViewById(R.id.fourdifbut1hint);
        hint[1] = (TextView) findViewById(R.id.fourdifbut2hint);
        hint[2] = (TextView) findViewById(R.id.fourdifbut3hint);
        hint[3] = (TextView) findViewById(R.id.fourdifbut4hint);
        start = (Button) findViewById(R.id.fourcentralstartbutton);
        time = (TextView) findViewById(R.id.fourtimecounter);
        point = (TextView) findViewById(R.id.fourpointcounter);
        level = (TextView) findViewById(R.id.fourleveltext);
        enablehint = (Button) findViewById(R.id.fourcallhint);
        enableplus = (TextView) findViewById(R.id.enableplus);
        SharedPreferences sharedpref = getSharedPreferences("gameinfo.txt", Context.MODE_PRIVATE);
        fourlevelnumber = sharedpref.getInt("fourlevel", 0);
        level.setText("LEVEL "+(fourlevelnumber+1));
        pointshopnum = sharedpref.getInt("point", 0);
        pointnum = pointnum+(20*sharedpref.getInt("fourlevel", 0));
        pointnum2 = pointnum;
        point.setText(""+pointnum);
        randomlevel = sharedpref.getInt("currentlevel", 1)+20;
        if (sharedpref.getInt("hintnumber", 3)==0) {
            enablehint.setAlpha(0.5f);
            enablehint.setEnabled(false);
        }
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

    public void callhint(int a) {
        if (nums[4]>nums[a]) {
            hint[a].setText(""+(nums[4]-nums[a]));
        } else {
            hint[a].setText(""+(nums[a]-nums[4]));
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

    public void newrandom(int i) {
        Random r = new Random();
        int rand = (r.nextInt((randomlevel)));
        nums[i]=rand;
        diffs[i].setText(""+rand);
    }

    public void check(int a) {
        int maxdif = 0;
        for (int j=0; j<4; j++) {
            if (maxdif < nums[j] - nums[4]) {
                maxdif = nums[j] - nums[4];
            } else {
                if (maxdif < nums[4] - nums[j]) {
                    maxdif = nums[4] - nums[j];
                }
            }
        }
        if ((maxdif==nums[a]-nums[4]) || (maxdif==nums[4]-nums[a])) {
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
        } else if ((nums[a]-nums[4])>(nums[4]-nums[a])) {
            pointnum=pointnum+nums[a]-nums[4];
            diffsanim[a+4].setVisibility(View.VISIBLE);
            call(diffsanim[a+4]);
        } else {
            pointnum=pointnum+nums[4]-nums[a];
            diffsanim[a+4].setVisibility(View.VISIBLE);
            call(diffsanim[a+4]);
        }
        if (pointnum<=0) {
            for (int i=0; i<=3; i++) {
                diffs[i].setEnabled(false);
            }
            for (int i=0; i<=4; i++) {
                call(diffs[i]);
            }
            call(time);
            call(point);
            call(level);
            for (int i=0; i<=3; i++) {
                call(hint[i]);
            }
            findViewById(R.id.fourwinrow).setVisibility(View.VISIBLE);
            AlphaAnimation animation1 = new AlphaAnimation(0f, 1f);
            animation1.setDuration(3000);
            animation1.setFillAfter(true);
            findViewById(R.id.fourwinrow).startAnimation(animation1);
            SharedPreferences sharedpref = getSharedPreferences("gameinfo.txt", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpref.edit();
            editor.putInt("fourlevel", sharedpref.getInt("fourlevel", 0)+1);
            if (hints==1) {
                editor.putInt("hintnumber", sharedpref.getInt("hintnumber", 3)-1);
                editor.apply();
            }
            editor.apply();
        }
        for (int i=0; i<=4; i++) {
            newrandom(i);
        }
        if (hints==1) {
            for (int i=0; i<=3; i++) {
                callhint(i);
            }
        }
        point.setText(""+pointnum);
    }

    public void fourstartgame(View V) {
        enablehint.setVisibility(View.GONE);
        enablehint.setEnabled(false);
        enableplus.setVisibility(View.GONE);
        findViewById(R.id.fourcentralstartbutton).setVisibility(View.GONE);
        for (int i=0; i<=3; i++) {
            diffs[i].setVisibility(View.VISIBLE);
        }
        findViewById(R.id.fourcentralbutton).setVisibility(View.VISIBLE);
        time.setVisibility(View.VISIBLE);
        for (int i=0; i<=4; i++) {
            newrandom(i);
        }
        if (hints==1) {
            for (int i=0; i<=3; i++) {
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
                for (int i=0; i<=3; i++) {
                    call(hint[i]);
                }
                for (int i=0; i<=3; i++) {
                    diffs[i].setEnabled(false);
                }
                for (int i=0; i<=4; i++) {
                    call(diffs[i]);
                }
                call(time);
                call(point);
                call(level);
                findViewById(R.id.fourfailrow).setVisibility(View.VISIBLE);
                AlphaAnimation animation1 = new AlphaAnimation(0f, 1f);
                animation1.setDuration(3000);
                animation1.setFillAfter(true);
                findViewById(R.id.fourfailrow).startAnimation(animation1);
                if (hints==1) {
                    SharedPreferences sharedpref = getSharedPreferences("gameinfo.txt", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.putInt("hintnumber", sharedpref.getInt("hintnumber", 3)-1);
                    editor.apply();
                }
            }
        }.start();
    }

    public void fourdiffbut1(View V) {
        check(0);
    }

    public void fourdiffbut2(View V) {
        check(1);
    }

    public void fourdiffbut3(View V) {
        check(2);
    }

    public void fourdiffbut4(View V) {
        check(3);
    }

    public void levelover(View V){
        onBackPressed();
    }
}
