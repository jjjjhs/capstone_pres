// src/pages/ResultPage.jsx
import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import WindowCard from '../components/WindowCard';
import Header     from '../components/Header';

export default function FeedbackPage() {
  const { state } = useLocation();
  const windows   = state?.windows || [];
  const navigate  = useNavigate();

  // return (
  //   <div style={{ padding: 20, maxWidth: 600, margin: 'auto' }}>
  //     <Header title="분석 결과"
  //             onClose={() => navigate(-1)} 
  //     />

  //     {windows.length === 0 && (
  //       <p>결과가 없습니다.</p>
  //     )}

  //     {windows.map((w, i) => (
  //       <WindowCard
  //         key={i}
  //         start={w.startSec}
  //         end={w.endSec}
  //         transcript={w.transcript}
  //         fillers={w.fillers}
  //         spm={w.spm}
  //         spmScore={w.spmScore}
  //       />
  //     ))}
  //   </div>
  // );

    return (
    <div style={{ display: 'flex', flexDirection: 'column', height: '100vh' }}>
      <Header title="분석 결과" onClose={() => nav(-1)} />
      <div style={{ flex: 1, overflowY: 'auto', padding: 16 }}>
        {windows.map((w,i) => (
          <WindowCard
            key={i}
            start={w.start}
            end={w.end}
            transcript={w.transcript}
            fillers={w.fillers}
            spm={w.spm}
            spmScore={w.spmScore}
            //  발표 정확도
            sentenceSimilarity={w.sentence_similarity}
            level={w.level}
          />
        ))}
      </div>
    </div>
  );
}
