import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import QCardGeneratorPage from './pages/QCardGeneratorPage';
import PracticePage       from './pages/PracticePage';
import FeedbackPage from './pages/FeedbackPage';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/"         element={<QCardGeneratorPage />} />
        <Route path="/QCardGenerator" element={<QCardGeneratorPage />} />
        <Route path="/practice" element={<PracticePage       />} />
        <Route path="/feedback"        element={<FeedbackPage         />} />
        <Route path="*"         element={<Navigate to="/" />} />
      </Routes>
    </BrowserRouter>
  );
}
