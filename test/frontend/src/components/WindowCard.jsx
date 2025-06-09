// src/components/WindowCard.jsx
import React from 'react';

/**
 * WindowCard 컴포넌트
 *
 * Props:
 *  - start    : number  (구간 시작 시간, 초 단위)
 *  - end      : number  (구간 끝 시간, 초 단위)
 *  - transcript: string (Whisper가 반환한 텍스트)
 *  - fillers  : object  ({ 단어: 횟수, … })
 *  - spm      : number  (30초 구간에서 계산된 분당 음절 수)
 *  - spmScore : number  (SPM을 점수화한 결과, 50~100 사이)
 */
export function WindowCard({ start, end, transcript, fillers, spm, spmScore, sentenceSimilarity, level }) {
  // 초(sec)를 “분:초” 형식으로 바꿔 주는 헬퍼 함수
  const formatTime = (sec) => {
    const m = Math.floor(sec / 60);
    const s = Math.floor(sec % 60);
    // ex) 0:05, 1:02
    return `${m}:${s.toString().padStart(2, '0')}`;
  };

  // fillers 객체를 “아: 2회, 어: 1회” 형태로 문자열화
  const fillerText =
    fillers && Object.keys(fillers).length > 0
      ? Object.entries(fillers)
          .map(([word, cnt]) => `${word}: ${cnt}회`)
          .join(', ')
      : '추임새 없음 👍';

  // fillerText에 따라 색상(red/green) 동적으로 결정
  const fillerColor =
    fillers && Object.keys(fillers).length > 0 ? 'crimson' : 'green';

  return (
    <div
      style={{
        marginBottom: '24px',
        padding: '16px',
        border: '1px solid #ddd',
        borderRadius: '8px',
        boxShadow: '0 2px 4px rgba(0,0,0,0.05)',
      }}
    >
      {/* 구간 시간 표시 */}
      <div style={{ fontWeight: 'bold', marginBottom: '8px' }}>
        {formatTime(start)} ~ {formatTime(end)} 초
      </div>

      {/* Whisper 텍스트 (스크롤이 길어질 수도 있으므로 pre-wrap) */}
      <div style={{ marginBottom: '8px' }}>
        <p style={{ whiteSpace: 'pre-wrap', lineHeight: 1.6 }}>{transcript}</p>
      </div>

      {/* 추임새 결과 */}
      <div style={{ marginBottom: '12px', color: fillerColor }}>
        {fillerText}
      </div>

      {/* SPM 및 SPM 점수 */}
      <div style={{ display: 'flex', gap: '24px', fontSize: '0.95rem' }}>
        <div>
          <strong>SPM:</strong> {spm} 음절/분
        </div>
        <div>
          <strong>Speed Score:</strong> {spmScore} 점
        </div>
      </div>

      {/* 발표 정확도 */}
      <div style={{ display: 'flex', gap: '24px', fontSize: '0.95rem' }}>
        <div>
          <strong>Similarity:</strong> {sentenceSimilarity}%
        </div>
        <div>
          <strong>Level:</strong> {level}
        </div>
      </div>
    </div>
  );
}

export default WindowCard;

