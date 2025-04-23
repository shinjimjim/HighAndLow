package main;

import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer 
{
	private static Clip bgmClip; // BGM用のClip（ループ再生用）
	
	//効果音を鳴らす
    public static void playSound(String soundFile) 
    {
        try 
        {
            InputStream input = SoundPlayer.class.getResourceAsStream("/sounds/" + soundFile);
            
            if (input == null) 
            {
                System.out.println("音声ファイルが見つかりません: " + soundFile);
                return;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(input);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();

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
                bgmClip.close();
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
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY); // 🔥 ループ再生
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

