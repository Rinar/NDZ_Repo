package com.task.soundcatch;
/**This Class was taken from http://habrahabr.ru/post/137708/ */
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Process;



public class StreamAudioRecord implements Runnable
{
    private boolean mIsRunning;
    private AudioFormat format;
    private AudioRecord mRecord;
    
    private final int BUFF_COUNT = 32;
    
    public StreamAudioRecord(AudioFormat format)
    {
        this.format = format;
    
        mIsRunning = true;
        mRecord = null;
    }
    
   
    public void stop()
    {
        mIsRunning = false;
       {
           
        }
    }
     public void fin()
        {
            // release res
            mRecord.release();
            mRecord = null;
        }
    
    
    @SuppressWarnings("static-access")
	public void run()
    {
        // приоритет для потока обработки аудио
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
        mIsRunning = true;
        
        int buffSize = AudioRecord.getMinBufferSize(44100 ,format.CHANNEL_IN_MONO, format.ENCODING_PCM_16BIT);
        
        if(buffSize == AudioRecord.ERROR)
        {
            System.err.println("getMinBufferSize returned ERROR");
            return;
        }
        
        if(buffSize == AudioRecord.ERROR_BAD_VALUE)
        {
            System.err.println("getMinBufferSize returned ERROR_BAD_VALUE");
            return;
        }
        
          
        
        short[][] buffers = new short[BUFF_COUNT][buffSize >> 1];
        
        mRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                44100, 
                format.CHANNEL_IN_MONO, format.ENCODING_PCM_16BIT,
                buffSize * 10);
        
        if(mRecord.getState() != AudioRecord.STATE_INITIALIZED)
        {
            System.err.println("getState() != STATE_INITIALIZED");
            return;
        }
        
        try
        {
            mRecord.startRecording();
        }
        catch(IllegalStateException e)
        {
            e.printStackTrace();
            return;
        }
        
        int count = 0;
        
        while(mIsRunning)
        {
            int samplesRead = mRecord.read(buffers[count], 0, buffers[count].length);
            
            if(samplesRead == AudioRecord.ERROR_INVALID_OPERATION)
            {
                System.err.println("read() returned ERROR_INVALID_OPERATION");
                return;
            }
            
            if(samplesRead == AudioRecord.ERROR_BAD_VALUE)
            {
                System.err.println("read() returned ERROR_BAD_VALUE");
                return;
            }
            try
            {
                mRecord.stop();
            }
            catch(IllegalStateException e)
            {
                e.printStackTrace();
                return;
            }
            
            count = (count + 1) % BUFF_COUNT;
        }

       
        
    }

   
}
