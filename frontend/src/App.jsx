import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Home from './pages/Home';
import Enlaces from './pages/Enlaces';

function App() {
  return (
    <Router>
      <nav style={{ padding: '1rem', borderBottom: '1px solid #ccc', display: 'flex', gap: '10px' }}>
        <Link to="/"><button>Home</button></Link>
        <Link to="/enlaces"><button>Enlaces</button></Link>
      </nav>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/enlaces" element={<Enlaces />} />
      </Routes>
    </Router>
  );
}

export default App;