import React, { useState } from 'react';
import axios from 'axios';

function App() {
  const [file, setFile] = useState(null);
  const [resultText, setResultText] = useState('');
  const [loading, setLoading] = useState(false);

  // 🎙️ 기존 녹음 기능 (주석처리)
  /*
  const recorderRef = useRef(null);
  const streamRef = useRef(null);
  const [recording, setRecording] = useState(false);
  const [recordedBlob, setRecordedBlob] = useState(null);

  const startRecording = async () => {const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
    streamRef.current = stream;
    const recorder = new RecordRTC(stream, {
      type: 'audio',
      mimeType: 'audio/wav',
      recorderType: RecordRTC.StereoAudioRecorder,
      numberOfAudioChannels: 1,
      desiredSampRate: 16000,
    });
    recorder.startRecording();
    recorderRef.current = recorder;
    setRecording(true);
    };
  const stopRecording = () => {
    if (!recorderRef.current) return;
    recorderRef.current.stopRecording(() => {
      const blob = recorderRef.current.getBlob();
      setRecordedBlob(blob);
      const url = URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'recorded.wav';
      a.click();
      URL.revokeObjectURL(url);
      streamRef.current?.getTracks().forEach(track => track.stop());
      setRecording(false);
    });
    };
  */

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const sendToServer = async () => {
    if (!file) {
      alert('업로드할 파일을 선택하세요.');
      return;
    }

    setLoading(true);
    setResultText('처리 중...');

    try {
      const formData = new FormData();
      formData.append('audio', file, file.name);

      const res = await axios.post('http://localhost:8080/api/whisper-multi', formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      });

      setResultText(res.data.result || '결과 없음');
    } catch (err) {
      console.error(err);
      setResultText('❌ 오류: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ textAlign: 'center', marginTop: '50px' }}>
      <h1>🎤 Whisper 음성 인식</h1>

      {/* 🎙️ 기존 녹음 버튼 (비활성화) */}
      {/* <button onClick={startRecording} disabled={recording}>녹음 시작</button>
      <button onClick={stopRecording} disabled={!recording}>녹음 종료 및 저장</button> */}

      {/* ⬇️ 파일 업로드 */}
      <input type="file" accept="audio/*" onChange={handleFileChange} />
      <button onClick={sendToServer}>서버로 전송</button>

      <h3 style={{ marginTop: '30px' }}>📝 인식 결과</h3>
      <p>{loading ? '⏳ 처리 중...' : resultText || '결과 없음'}</p>
    </div>
  );
}

export default App;
