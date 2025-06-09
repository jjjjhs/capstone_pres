// src/api/evaluate.js
import axios from 'axios';

/**
 * cue: string
 * transcripts: string[]
 * returns: Array<{ sentence_similarity: number, level: string }>
 */
export async function evaluateAccuracy(cue, transcripts) {
  const payload = { cue, transcripts };
  const res = await axios.post(
    'http://localhost:8080/api/evaluate', //URL 수정하기
    payload,
    { headers: { 'Content-Type': 'application/json' } }
  );
  return res.data; 
  // 예시: [ { sentence_similarity: 0.82, level: "Good" }, … ]
}
