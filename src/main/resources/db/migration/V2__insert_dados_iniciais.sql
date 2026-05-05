INSERT INTO modalidades (nome) VALUES
('Musculação'),
('Funcional'),
('Jiu-Jitsu'),
('Muay Thai');


INSERT INTO planos (modalidade_id, nome, valor_mensal)
SELECT id, 'MENSAL', 120.00 FROM modalidades WHERE nome = 'Musculação';

INSERT INTO planos (modalidade_id, nome, valor_mensal)
SELECT id, 'TRIMESTRAL', 330.00 FROM modalidades WHERE nome = 'Musculação';

INSERT INTO planos (modalidade_id, nome, valor_mensal)
SELECT id, 'MENSAL', 150.00 FROM modalidades WHERE nome = 'Funcional';

INSERT INTO planos (modalidade_id, nome, valor_mensal)
SELECT id, 'MENSAL', 180.00 FROM modalidades WHERE nome = 'Jiu-Jitsu';

INSERT INTO graduacoes (modalidade_id, nome)
SELECT id, 'Faixa Branca' FROM modalidades WHERE nome = 'Jiu-Jitsu';

INSERT INTO graduacoes (modalidade_id, nome)
SELECT id, 'Faixa Azul' FROM modalidades WHERE nome = 'Jiu-Jitsu';

INSERT INTO graduacoes (modalidade_id, nome)
SELECT id, 'Faixa Roxa' FROM modalidades WHERE nome = 'Jiu-Jitsu';
