package com.warm.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.warm.aidldemo.bean.Person;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bindService)
    Button bindService;
    @BindView(R.id.add)
    Button add;
    @BindView(R.id.getInfo)
    Button getInfo;
    @BindView(R.id.detail)
    TextView detail;

    private Intent serviceIntent;
    private IPersonInterface iPersonInterface;
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(MainActivity.this, "启动成功", Toast.LENGTH_SHORT).show();
            iPersonInterface=IPersonInterface.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "关闭成功", Toast.LENGTH_SHORT).show();
            iPersonInterface=null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        serviceIntent =new Intent(this,MyPersonService.class);

    }

    @OnClick({R.id.bindService, R.id.add, R.id.getInfo})
    public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.bindService:
                    if (iPersonInterface==null) {
                        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
                    }else{
                        Toast.makeText(this, "已连接", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.add:
                    Person person=new Person(String.valueOf(new Random().nextInt(100)));
                    try {
                        iPersonInterface.addPerson(person);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.getInfo:
                    try {
                        detail.setText(iPersonInterface.getPersonList().toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
