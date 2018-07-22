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

public class EightButtonMode extends AppCompatActivity {

    Button eightdiffs[] = new Button[9], start, eightdiffsanim[] = new Button[16], enablehint;
    TextView time, point, level, hint[]=new TextView[8], enableplus;
    int nums[] = new int[9], inttime=60, pointnum=100, pointnum2, pointshopnum, levelnumber, randomlevel, hints=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.eightbuttonmode);
        eightdiffs[0] = (Button) findViewById(R.id.eightdifbut1);
        eightdiffs[1] = (Button) findViewById(R.id.eightdifbut2);
        eightdiffs[2] = (Button) findViewById(R.id.eightdifbut3);
        eightdiffs[3] = (Button) findViewById(R.id.eightdifbut4);
        eightdiffs[4] = (Button) findViewById(R.id.eightdifbut5);
        eightdiffs[5] = (Button) findViewById(R.id.eightdifbut6);
        eightdiffs[6] = (Button) findViewById(R.id.eightdifbut7);
        eightdiffs[7] = (Button) findViewById(R.id.eightdifbut8);
        eightdiffs[8] = (Button) findViewById(R.id.eightcentralbutton);
        hint[0]= (TextView) findViewById(R.id.eightdifbut1hint);
        hint[1]= (TextView) findViewById(R.id.eightdifbut2hint);
        hint[2]= (TextView) findViewById(R.id.eightdifbut3hint);
        hint[3]= (TextView) findViewById(R.id.eightdifbut4hint);
        hint[4]= (TextView) findViewById(R.id.eightdifbut5hint);
        hint[5]= (TextView) findViewById(R.id.eightdifbut6hint);
        hint[6]= (TextView) findViewById(R.id.eightdifbut7hint);
        hint[7]= (TextView) findViewById(R.id.eightdifbut8hint);
        eightdiffsanim[0] = (Button) findViewById(R.id.eightdifbut1green);
        eightdiffsanim[1] = (Button) findViewById(R.id.eightdifbut2green);
        eightdiffsanim[2] = (Button) findViewById(R.id.eightdifbut3green);
        eightdiffsanim[3] = (Button) findViewById(R.id.eightdifbut4green);
        eightdiffsanim[4] = (Button) findViewById(R.id.eightdifbut5green);
        eightdiffsanim[5] = (Button) findViewById(R.id.eightdifbut6green);
        eightdiffsanim[6] = (Button) findViewById(R.id.eightdifbut7green);
        eightdiffsanim[7] = (Button) findViewById(R.id.eightdifbut8green);
        eightdiffsanim[8] = (Button) findViewById(R.id.eightdifbut1red);
        eightdiffsanim[9] = (Button) findViewById(R.id.eightdifbut2red);
        eightdiffsanim[10] = (Button) findViewById(R.id.eightdifbut3red);
        eightdiffsanim[11] = (Button) findViewById(R.id.eightdifbut4red);
        eightdiffsanim[12] = (Button) findViewById(R.id.eightdifbut5red);
        eightdiffsanim[13] = (Button) findViewById(R.id.eightdifbut6red);
        eightdiffsanim[14] = (Button) findViewById(R.id.eightdifbut7red);
        eightdiffsanim[15] = (Button) findViewById(R.id.eightdifbut8red);
        enablehint = (Button) findViewById(R.id.enablehint);
        start = (Button) findViewById(R.id.eightcentralstartbutton);
        time = (TextView) findViewById(R.id.eighttimecounter);
        point = (TextView) findViewById(R.id.eightpointcounter);
        level = (TextView) findViewById(R.id.eightleveltext);
        enableplus = (TextView) findViewById(R.id.enableplus);
        SharedPreferences sharedpref = getSharedPreferences("gameinfo.txt", Context.MODE_PRIVATE);
        levelnumber = sharedpref.getInt("eightlevel", 0);
        level.setText("LEVEL "+(levelnumber+1));
        pointshopnum = sharedpref.getInt("point", 0);
        pointnum = pointnum+(20*sharedpref.getInt("eightlevel", 0));
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

    public void call(View v) {
        v.setAlpha(1f);
        AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0f);
        animation1.setDuration(1500);
        animation1.setFillAfter(true);
        v.startAnimation(animation1);
        v.setVisibility(View.GONE);
    }

    public void callhint(int a) {
        if (nums[8]>nums[a]) {
            hint[a].setText(""+(nums[8]-nums[a]));
        } else {
            hint[a].setText(""+(nums[a]-nums[8]));
        }
    }

    public void newrandom(int i) {
        Random r = new Random();
        int rand = (r.nextInt(randomlevel));
        nums[i]=rand;
        eightdiffs[i].setText(""+rand);
    }

    public void check(int a) {
        int maxdif = 0;
        for (int j=0; j<8; j++) {
            if (maxdif < nums[j] - nums[8]) {
                maxdif = nums[j] - nums[8];
            } else {
                if (maxdif < nums[8] - nums[j]) {
                    maxdif = nums[8] - nums[j];
                }
            }
        }
        if ((maxdif==nums[a]-nums[8]) || (maxdif==nums[8]-nums[a])) {
            pointnum=pointnum-maxdif;
            call(eightdiffsanim[a]);
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
        } else if ((nums[a]-nums[8])>(nums[8]-nums[a])) {
            pointnum=pointnum+nums[a]-nums[8];
            eightdiffsanim[a+8].setVisibility(View.VISIBLE);
            call(eightdiffsanim[a+8]);
        } else {
            pointnum=pointnum+nums[8]-nums[a];
            eightdiffsanim[a+8].setVisibility(View.VISIBLE);
            call(eightdiffsanim[a+8]);
        }
        if (pointnum<=0) {
            for (int i=0; i<=7; i++) {
                eightdiffs[i].setEnabled(false);
            }
            for (int i=0; i<=8; i++) {
                call(eightdiffs[i]);
            }
            call(time);
            call(point);
            call(level);
            for (int i=0; i<=7; i++) {
                call(hint[i]);
            }
            findViewById(R.id.eightwinrow).setVisibility(View.VISIBLE);
            AlphaAnimation animation1 = new AlphaAnimation(0f, 1f);
            animation1.setDuration(3000);
            animation1.setFillAfter(true);
            findViewById(R.id.eightwinrow).startAnimation(animation1);
            SharedPreferences sharedpref = getSharedPreferences("gameinfo.txt", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpref.edit();
            editor.putInt("eightlevel", levelnumber+1);
            if (hints==1) {
                editor.putInt("hintnumber", sharedpref.getInt("hintnumber", 3)-1);
                editor.apply();
            }
            editor.apply();
        }
        for (int i=0; i<=8; i++) {
            newrandom(i);
        }
        if (hints==1) {
            for (int i=0; i<=7; i++) {
                callhint(i);
            }
        }
        point.setText(""+pointnum);
    }

    public void startgame(View V) {
        enablehint.setVisibility(View.GONE);
        enablehint.setEnabled(false);
        enableplus.setVisibility(View.GONE);
        findViewById(R.id.eightcentralstartbutton).setVisibility(View.GONE);
        for (int i=0; i<9; i++) {
            eightdiffs[i].setVisibility(View.VISIBLE);
        }
        findViewById(R.id.eightcentralbutton).setVisibility(View.VISIBLE);
        time.setVisibility(View.VISIBLE);
        for (int i=0; i<9; i++) {
            newrandom(i);
        }
        if (hints==1) {
            for (int i=0; i<=7; i++) {
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
                for (int i=0; i<=7; i++) {
                    call(hint[i]);
                }
                for (int i=0; i<=8; i++) {
                    call(eightdiffs[i]);
                }
                for (int i=0; i<=7; i++) {
                    eightdiffs[i].setEnabled(false);
                }
                call(time);
                call(point);
                call(level);
                SharedPreferences sharedpref = getSharedPreferences("gameinfo.txt", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpref.edit();
                if (hints==1) {
                    editor.putInt("hintnumber", sharedpref.getInt("hintnumber", 3)-1);
                    editor.apply();
                }
                findViewById(R.id.eightfailrow).setVisibility(View.VISIBLE);
                AlphaAnimation animation1 = new AlphaAnimation(0f, 1f);
                animation1.setDuration(3000);
                animation1.setFillAfter(true);
                findViewById(R.id.eightfailrow).startAnimation(animation1);
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

    public void diffbut7(View V) {
        check(6);
    }

    public void diffbut8(View V) {
        check(7);
    }

    public void overtap(View V){
        onBackPressed();
    }
}