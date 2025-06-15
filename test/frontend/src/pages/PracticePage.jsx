// src/pages/PracticePage.jsx
import React, { useState, useRef } from 'react';
import { analyseAudio }          from '../api/analyse';
import { evaluateAccuracy }    from '../api/evaluate';
import { useNavigate }           from 'react-router-dom';
import Header                    from '../components/Header';
import Button                    from '../components/Button';
import sampleSlide from '../assets/slide.png'

export default function PracticePage() {
  const navigate = useNavigate();

  const [recording, setRecording] = useState(false);
  const [loading,   setLoading]   = useState(false);
  const mediaRef = useRef({ recorder: null, stream: null, chunks: [] });

  const startRec = async () => {
    const stream   = await navigator.mediaDevices.getUserMedia({ audio: true });
    const recorder = new MediaRecorder(stream);
    mediaRef.current = { recorder, stream, chunks: [] };

    recorder.ondataavailable = e => mediaRef.current.chunks.push(e.data);

    recorder.onstop = async () => {
      const blob = new Blob(mediaRef.current.chunks, { type: 'audio/webm' });
      setLoading(true);
      try {
        const windows = await analyseAudio(blob);

        const cue = /* 큐카드 문장 */"안녕하세요. 오늘은 대학생 발표 불안과 그 해결 방안에 대해 발표하겠습니다 ";

        const transcripts = windows
            .map(w => w.transcripts) // 여기가 실제 문자열인지 확인
            .filter(t => typeof t === 'string' && t.trim() !== '');

        // sentence_similarity / level API 호출
        const evals = await evaluateAccuracy(cue, transcripts);

        const merged = windows.map((w, i) => {
          const evalResult = evals[i] || {
            sentence_similarity: 0,
            level: '없음'
          };
          return {
            ...w,
            sentence_similarity: Math.round(evalResult.sentence_similarity * 100),
            level: evalResult.level
          };
        });
        // windows 에 두 필드를 병합
        // const merged = windows.map((w, i) => ({
        //   ...w,
        //   sentence_similarity: Math.round(evalResult.sentence_similarity * 100), // 0~100%
        //   level: evalResult.level,
        // }));

        // FeedbackPage로 넘기기
        navigate('/feedback', { state: { windows: merged } });

      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
        mediaRef.current.stream.getTracks().forEach(t => t.stop());
      }
    };

    recorder.start();
    setRecording(true);
  };

  const stopRec = () => {
    mediaRef.current.recorder.stop();
    setRecording(false);
  };

  return (
    <div style={{
      display: 'flex', flexDirection: 'column', height: '100vh'
    }}>
      <Header title="캡스톤디자인 > 중간발표"
              onClose={() => {}}
      />

      {/* <div style={{
        flex: 1,
        background: '#f5f6f7',
        display: 'flex', alignItems: 'center', justifyContent: 'center'
      }}>
        <span style={{ color: '#999', fontSize: 18 }}>
          발표자료 pdf(pptx)1페이지
        </span>
      </div> */}

      <div style={{
        flex: 1,
        background: '#f5f6f7',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        overflow: 'auto'
      }}>
        <img
          src={sampleSlide}
          alt="슬라이드 예시"
          style={{ maxWidth: '90%', maxHeight: '90%' }}
        />
      </div>

      <div style={{
        padding: 16,
        display: 'flex',
        alignItems: 'center'
      }}>
        <Button onClick={ recording ? stopRec : startRec }>
          { recording ? '종료' : '시작' }
        </Button>

        <div style={{ marginLeft: 'auto' }}>
          <Button>QR</Button>
        </div>
      </div>
    </div>
  );
}
