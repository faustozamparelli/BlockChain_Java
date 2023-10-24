import java.lang.reflect.Field;
import java.util.ArrayList;

import net.Network;
import userpack.User;

public class Main { public static void main(String[] args) throws Exception {
        Thread.currentThread().setPriority(10);
        Network net= new Network(5, 10);
        net.start();
        User Peppe = new User(net, "PEPPE"); // USER - 0
        User Marco = new User(net, "MARCO"); // USER - 1
        Peppe.mine();
        Marco.mine();
        while (net.BlockChain.size() < 5) {try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}}
        //BOCK 5
        User Cola = new User(net, "COLA"); // USER - 2
        Cola.mine();
        User Micu = new User(net, "MICU"); // USER - 3
        User Vici = new User(net, "VICI"); // USER - 4
        Peppe.give(Marco.UserNumber, 6,4);
        Peppe.give(Vici.UserNumber, 6,1);
        Marco.give(Vici.UserNumber,3,2);
        Marco.give(Peppe.UserNumber,3,1);
        while (net.BlockChain.size() < 7) {try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}}
        User Alieno = new User(net, "ALIENO"); // USER - 5
        //BOCK 7
        Peppe.give(Alieno.UserNumber, 1,0);
        Vici.give(Alieno.UserNumber, 1, 0);
        Marco.give(Alieno.UserNumber, 1,0);


        while (net.BlockChain.size() < 10) {try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}}
        //BOCK 10
        Vici.mine();
        Peppe.stopmining();
        Vici.give(Cola.UserNumber, 6,1);
        Peppe.give(Micu.UserNumber, 7,3);
        Marco.give(Micu.UserNumber,1,1);
        //Badu User example
        User BadUser = new User(net, "BADUSER");
        Field badwallet = BadUser.getClass().getDeclaredField("wallet");
        badwallet.setAccessible(true);
        int[] fakereference1= {71, 1, 3};
        int[] fakereference2= {100, 2, 3};
        ((ArrayList<int[]>)(badwallet.get( BadUser))).add(fakereference1);
        ((ArrayList<int[]>)(badwallet.get( BadUser))).add(fakereference2);
        BadUser.give(Alieno.UserNumber, 44, 2);
        BadUser.give(Peppe.UserNumber, 88, 10);
        while (net.BlockChain.size() < 13) {try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}}
        //BOCK 10
        User Thanos= new User(net, "THANOS"); // USER - 6
        Alieno.mine();
        Alieno.give(Thanos.UserNumber, 3, 0);
        BadUser.give(Peppe.UserNumber, 88, 10);
        while (net.BlockChain.size() < 15) {try {Thread.sleep(100); } catch (InterruptedException e) {e.printStackTrace();}}
        //BOCK 15
        Marco.stopmining();
        Micu.mine();
        Micu.give(Cola.UserNumber,1,0);
        Cola.give(Peppe.UserNumber, 11,1);
        Peppe.give(Vici.UserNumber, 7,3);
        Vici.give(Vici.UserNumber,1,1);
        Marco.give(Micu.UserNumber,2,1);

        while (net.BlockChain.size() < 19) {try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}}
        //BOCK 19
        User Santino = new User(net, "SANTINO");// USER - 7
        Santino.mine();
        Cola.stopmining();
        Micu.give(Santino.UserNumber,12,0);
        Cola.give(Peppe.UserNumber, 11,1);
        Peppe.give(Vici.UserNumber, 7,3);
        while (net.BlockChain.size() < 20) {try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}}
        //BOCK 20
        Alieno.give(Thanos.UserNumber, 10, 0);
        while (net.BlockChain.size() < 22) {try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}}
        //BOCK 25
        Peppe.give(Alieno.UserNumber, 10, 4);
        Marco.give(Alieno.UserNumber, 10, 4);
        while (net.BlockChain.size() < 25) {try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}}
        //BOCK 25
        Alieno.give(Thanos.UserNumber, 20, 0);
        Santino.give(Vici.UserNumber,8,1);
        Micu.stopmining();
        Santino.stopmining();
        Alieno.give(Thanos.UserNumber, 14, 0);
        while (net.BlockChain.size() < 27) {try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}}
        //BOCK 27
        User Draghi = new User(net, "MARIO DRAGHI");// USER - 8
        Draghi.mine();
        Micu.give(Draghi.UserNumber, 12, 4);
        Thanos.give(Draghi.UserNumber, 23, 0);
        while (net.BlockChain.size() < 32) {try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}}
        //BOCK 32
        Draghi.stopmining();
        Alieno.stopmining();
        Vici.stopmining();
        net.stopnet();
        }
}