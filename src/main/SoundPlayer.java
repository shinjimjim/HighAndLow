package main;

import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip; //Javaで 音声（WAV形式）を再生 するためのオブジェクト

public class SoundPlayer 
{	//static:クラス全体で共通の1つだけ存在する（インスタンスを作らなくても使える）
	private static Clip bgmClip; // BGM用のClip（ループ再生用）
	
	//効果音を鳴らす
    public static void playSound(String soundFile) 
    {
        try 
        {
        	//getResourceAsStream() で音声ファイルを読み込む（クラスパスから取得）
            InputStream input = SoundPlayer.class.getResourceAsStream("/sounds/" + soundFile); 
            
            if (input == null) 
            {
                System.out.println("音声ファイルが見つかりません: " + soundFile);
                return;
            }
            //AudioSystem.getAudioInputStream() で音声データに変換
            AudioInputStream audio = AudioSystem.getAudioInputStream(input);
            Clip clip = AudioSystem.getClip(); //AudioSystem.getClip() で Clip を作成
            clip.open(audio); //clip.open() で音声を読み込み
            clip.start(); //clip.start() で再生！
        } 
        catch (Exception e) 
        {
            System.out.println("エラー: 音声ファイルが再生できません！");
            e.printStackTrace();
        }
    }
    
    // BGMをループ再生する
    public static void playBGM(String bgmFile) 
    {
        try 
        {
            // すでにBGMが流れていたら停止
            if (bgmClip != null && bgmClip.isRunning()) 
            {
                bgmClip.stop();
                bgmClip.close(); //新しいBGMが再生される前に、前のBGMを stop() と close() で止める
            }

            InputStream input = SoundPlayer.class.getResourceAsStream("/sounds/" + bgmFile);
            if (input == null) 
            {
                System.out.println("BGMファイルが見つかりません: " + bgmFile);
                return;
            }
            AudioInputStream audio = AudioSystem.getAudioInputStream(input);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audio);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY); // Clip.LOOP_CONTINUOUSLY を使って、無限ループ再生
            bgmClip.start();
        } 
        catch (Exception e) 
        {
            System.out.println("エラー: BGMが再生できません！");
            e.printStackTrace();
        }
    }

    // BGMを停止する
    public static void stopBGM() 
    {
        if (bgmClip != null && bgmClip.isRunning()) 
        {
            bgmClip.stop();
            bgmClip.close();
        }
    }
}

