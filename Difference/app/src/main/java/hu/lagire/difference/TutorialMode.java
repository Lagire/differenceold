package hu.lagire.difference;

import android.os.Bundle;
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

public class TutorialMode extends AppCompatActivity {

    Button diffs[] = new Button[5], start, diffsanim[] = new Button[8], tutorialtexthelp;
    TextView point, tutorialtext, hint[]=new TextView[4];
    int nums[] = new int[5], pointnum=100, tutorialprogress=0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.tutorialmode);
        diffs[0] = (Button) findViewById(R.id.tutdifbut1);
        diffs[1] = (Button) findViewById(R.id.tutdifbut2);
        diffs[2] = (Button) findViewById(R.id.tutdifbut3);
        diffs[3] = (Button) findViewById(R.id.tutdifbut4);
        diffs[4] = (Button) findViewById(R.id.tutcentralbutton);
        diffsanim[0] = (Button) findViewById(R.id.tutdifbut1green);
        diffsanim[1] = (Button) findViewById(R.id.tutdifbut2green);
        diffsanim[2] = (Button) findViewById(R.id.tutdifbut3green);
        diffsanim[3] = (Button) findViewById(R.id.tutdifbut4green);
        diffsanim[4] = (Button) findViewById(R.id.tutdifbut1red);
        diffsanim[5] = (Button) findViewById(R.id.tutdifbut2red);
        diffsanim[6] = (Button) findViewById(R.id.tutdifbut3red);
        diffsanim[7] = (Button) findViewById(R.id.tutdifbut4red);
        hint[0] = (TextView) findViewById(R.id.tutdifbut1hint);
        hint[1] = (TextView) findViewById(R.id.tutdifbut2hint);
        hint[2] = (TextView) findViewById(R.id.tutdifbut3hint);
        hint[3] = (TextView) findViewById(R.id.tutdifbut4hint);
        start = (Button) findViewById(R.id.tutcentralstartbutton);
        point = (TextView) findViewById(R.id.tutpointcounter);
        point.setText(""+pointnum);
        point.setVisibility(View.VISIBLE);
        tutorialtext = (TextView) findViewById(R.id.tutorialtext);
        tutorialtexthelp = (Button) findViewById(R.id.tutorialtexthelp);
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
        if (nums[4]>nums[a]) {
            hint[a].setText(""+(nums[4]-nums[a]));
        } else {
            hint[a].setText(""+(nums[a]-nums[4]));
        }
    }

    public void newrandom(int i) {
        Random r = new Random();
        int rand = (r.nextInt(20));
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
            for (int i=0; i<=4; i++) {
                call(diffs[i]);
            }
            for (int i=0; i<=3; i++) {
                call(hint[i]);
            }
            call(point);
            findViewById(R.id.tutwinrow).setVisibility(View.VISIBLE);
            AlphaAnimation animation1 = new AlphaAnimation(0f, 1f);
            animation1.setDuration(3000);
            animation1.setFillAfter(true);
        }
        for (int i=0; i<=4; i++) {
            newrandom(i);
        }
        for (int i=0; i<=3; i++) {
            callhint(i);
        }
        point.setText(""+pointnum);
    }


    public void fourstartgame(View V) {

        findViewById(R.id.tutcentralstartbutton).setVisibility(View.GONE);
        for (int i=0; i<=3; i++) {
            diffs[i].setVisibility(View.VISIBLE);
        }
        findViewById(R.id.tutcentralbutton).setVisibility(View.VISIBLE);
        for (int i=0; i<=4; i++) {
            newrandom(i);
        }
        for (int i=0; i<=3; i++) {
            callhint(i);
        }
        tutorialtext.setText("Your goal is to reach 0 point.");
        findViewById(R.id.tutorialtexthelp).setVisibility(View.VISIBLE);
        tutorialprogress++;
    }

    public void tutorialprogress(View V) {
        if (tutorialprogress==13) {
            tutorialtext.setText("The hints are shown in the tutorial.");
            tutorialtext.setVisibility(View.INVISIBLE);
            findViewById(R.id.tutorialtexthelp).setVisibility(View.INVISIBLE);
        }
        if (tutorialprogress==12) {
            tutorialtext.setText("This text contains the difference of the number.");
            tutorialprogress++;
            for (int i=0; i<=3; i++) {
                hint[i].setVisibility(View.VISIBLE);
            }
        }
        if (tutorialprogress==11) {
            tutorialtext.setText("You can get hints. If you choose hints, a small text will appear above each button.");
            tutorialprogress++;
        }
        if (tutorialprogress==10) {
            tutorialtext.setText("You can buy upgrades at the shop to go even further.");
            tutorialprogress++;
        }
        if (tutorialprogress==9) {
            tutorialtext.setText("This time limit is not allowed in tutorial mode.");
            tutorialprogress++;
        }
        if (tutorialprogress==8) {
            tutorialtext.setText("You need to make 0 point in a certain amount of time.");
            tutorialprogress++;
        }
        if (tutorialprogress==7) {
            tutorialtext.setText("In that case, every of them will be agreed.");
            tutorialprogress++;
        }
        if (tutorialprogress==6) {
            tutorialtext.setText("It is possible to get more than one number what gives you te greatest difference.");
            tutorialprogress++;
        }
        if (tutorialprogress==5) {
            tutorialtext.setText("The right always the one what gives you the greatest difference compared to the central button.");
            tutorialprogress++;
        }
        if (tutorialprogress==4) {
            tutorialtext.setText("If you choose the wrong number, your point will be greater.");
            tutorialprogress++;
        }if (tutorialprogress==3) {
            tutorialtext.setText("If you choose the right number, your point will be lowered.");
            tutorialprogress++;
        }
        if (tutorialprogress==2) {
            tutorialtext.setText("You can see four buttons around the central button.");
            tutorialprogress++;
        }
        if (tutorialprogress==1) {
            tutorialtext.setText("Your point will change every time you choose a button.");
            tutorialprogress++;
        }
    }


    public void tutdiffbut1(View V) {
        check(0);
    }

    public void tutdiffbut2(View V) {
        check(1);
    }

    public void tutdiffbut3(View V) {
        check(2);
    }

    public void tutdiffbut4(View V) {
        check(3);
    }


    public void levelover(View V){
        onBackPressed();
    }
}
