package com.coutigames.main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	public static class Clips{
		public Clip[] clips;
		private int p;
		private int count;
		
		public Clips(byte[] buffer, int count) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
			if(buffer == null)
				return;
				
			clips = new Clip[count];
			this.count = count;
			
			for(int i = 0; i < count; i++) {
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new java.io.ByteArrayInputStream(buffer)));
			}
		}
		
		public void play() {
			if(clips == null) return;
			clips[p].stop();
			clips[p].setFramePosition(0);
			clips[p].start();
			p++;
			if(p >= count) p = 0;
		}
		
		public void loop() {
			if(clips == null) return;
			clips[p].loop(300);
		}
		
		public void stop() {
			if(clips == null) return;
			clips[p].stop();
		}
	}
	
	public static Clips Music_01;
	public static Clips Colis_Check;
	public static Clips Colis_Vida;
	public static Clips Dano;
	public static Clips PowerUp;
	public static Clips LevelComplete;
	public static Clips GameOver;
	
	static {
		Music_01 = load("/Music_01.wav",1);
		Colis_Check = load("/Colis_Check.wav",1);
		Colis_Vida = load("/Colis_Vida.wav",1);
		Dano = load("/hurt.wav",1);
		PowerUp = load("/Colis_Check.wav",1); // Reutilizando som existente
		LevelComplete = load("/Colis_Check.wav",1); // Reutilizando som existente
		GameOver = load("/hurt.wav",1); // Reutilizando som existente
	}
	
	private static Clips load(String name,int count) {
		try {
			java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
			java.io.DataInputStream dis = new java.io.DataInputStream(Sound.class.getResourceAsStream(name));
			
			byte[] buffer = new byte[1024];
			int read;
			while((read = dis.read(buffer)) >= 0) {
				baos.write(buffer,0,read);
			}
			dis.close();
			byte[] data2 = baos.toByteArray();
			return new Clips(data2,count);
		}catch(Exception e) {
			try {
				return new Clips(null,0);
			}catch(Exception ee) {
				return null;
			}
		}
	}
}
