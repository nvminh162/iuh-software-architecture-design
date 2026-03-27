import { useState, useEffect } from 'react';
import './index.css';

function App() {
  const [searchTerm, setSearchTerm] = useState('');
  const [debouncedTerm, setDebouncedTerm] = useState('');
  const [results, setResults] = useState([]);
  const [isSearching, setIsSearching] = useState(false);

  // Debounce logic
  useEffect(() => {
    const handler = setTimeout(() => {
      setDebouncedTerm(searchTerm);
    }, 500); // 500ms delay

    // Cleanup the timeout if user types within 500ms
    return () => {
      clearTimeout(handler);
    };
  }, [searchTerm]);

  // Search logic
  useEffect(() => {
    if (!debouncedTerm) {
      setResults([]);
      return;
    }

    const fetchResults = async () => {
      setIsSearching(true);
      try {
        const response = await fetch(`http://localhost:8080/api/products/search?q=${encodeURIComponent(debouncedTerm)}`);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setResults(data);
      } catch (error) {
        console.error("Error fetching data:", error);
        setResults([]);
      } finally {
        setIsSearching(false);
      }
    };

    fetchResults();
  }, [debouncedTerm]);

  return (
    <div className="search-container">
      <h1>Tech Finder</h1>
      <div className="search-input-wrapper">
        <input
          type="text"
          className="search-input"
          placeholder="Search for premium gadgets..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      <div className="search-results">
        {isSearching && <div className="loading">Searching...</div>}
        
        {!isSearching && searchTerm && results.length === 0 && (
          <div className="no-results">No products found for "{searchTerm}"</div>
        )}

        {!isSearching && results.length > 0 && results.map((product) => (
          <div key={product.id} className="search-result-card">
            <h3>{product.name}</h3>
            <p>{product.description}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;
