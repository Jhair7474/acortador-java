import { useState } from 'react';
import '../css/Home.css'; 

const Home = () => {
  const [form, setForm] = useState({ originalUrl: '', imageUrl: '', description: '' });

  const handleAcortar = async () => {
    if (!form.originalUrl || !form.description) {
      alert("Por favor rellena la URL y la descripción");
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/api/links', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form)
      });

      if (response.ok) {
        const shortUrl = await response.text();
        setForm({ ...form, originalUrl: shortUrl });
        alert("¡Enlace acortado con éxito!");
      } else {
        const errorMsg = await response.text();
        alert("Error: " + errorMsg);
      }
    } catch (err) {
      alert("No se pudo conectar con el servidor.");
    }
  };

  return (
    <div className="home-container">
      <h2>Acortar Nuevo Enlace</h2>
      <div className="input-group">
        <input 
          type="text" 
          placeholder="Pegar enlace (URL)" 
          className={`input-field ${form.originalUrl.includes('localhost:8080') ? 'shortened' : ''}`}
          value={form.originalUrl}
          onChange={e => setForm({...form, originalUrl: e.target.value})} 
        />
        <input 
          type="text" 
          placeholder="URL de la imagen" 
          className="input-field"
          value={form.imageUrl}
          onChange={e => setForm({...form, imageUrl: e.target.value})} 
        />
        <textarea 
          placeholder="Descripción (mín. 5 palabras)" 
          className="input-field textarea-field"
          value={form.description}
          onChange={e => setForm({...form, description: e.target.value})} 
        />
        <button className="btn-acortar" onClick={handleAcortar}>
          Acortar
        </button>
      </div>
    </div>
  );
};

export default Home;