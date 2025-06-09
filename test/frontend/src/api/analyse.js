import axios from 'axios';

export async function analyseAudio(blob) {
  const form = new FormData();
  form.append('audio', blob, 'recorded.webm');
  const res = await axios.post(
    'http://localhost:8080/api/analyse',
    form,
    { headers: { 'Content-Type': 'multipart/form-data' } }
  );
  return res.data; // List<WindowDto>
}
