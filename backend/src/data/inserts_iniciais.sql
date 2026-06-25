INSERT INTO Turma (curso, bloco_atual) VALUES 
('Tecnologia em Sistemas para Internet', '1'),
('Tecnologia em Sistemas para Internet', '2'),
('Tecnologia em Sistemas para Internet', '3'),
('Tecnologia em Sistemas para Internet', '4'),
('Tecnologia em Sistemas para Internet', '5'),
('Ciência da Computação', '1'),
('Ciência da Computação', '2'),
('Ciência da Computação', '3'),
('Ciência da Computação', '4'),
('Ciência da Computação', '5');

INSERT INTO Aluno (nome, email, telefone, turma_id) VALUES 
('Lucas Mendes', 'lucas.mendes@email.com', '86999990001', 1),
('Mariana Silva', 'mariana.silva@email.com', '86999990002', 1),
('Pedro Henrique', 'pedro.henrique@email.com', '86999990003', 2),
('Ana Beatriz', 'ana.beatriz@email.com', '86999990004', 2),
('Carlos Eduardo', 'carlos.edu@email.com', '86999990005', 3),
('Fernanda Costa', 'fernanda.costa@email.com', '86999990006', 3),
('Rafael Souza', 'rafael.souza@email.com', '86999990007', 4),
('Camila Lima', 'camila.lima@email.com', '86999990008', 4),
('João Victor', 'joao.victor@email.com', '86999990009', 5),
('Juliana Alves', 'juliana.alves@email.com', '86999990010', 5),
('Marcos Antônio', 'marcos.antonio@email.com', '86999990011', 6),
('Beatriz Ribeiro', 'beatriz.ribeiro@email.com', '86999990012', 6),
('Gabriel Martins', 'gabriel.martins@email.com', '86999990013', 7),
('Larissa Dias', 'larissa.dias@email.com', '86999990014', 7),
('Thiago Rocha', 'thiago.rocha@email.com', '86999990015', 8),
('Letícia Carvalho', 'leticia.carvalho@email.com', '86999990016', 8),
('Felipe Gomes', 'felipe.gomes@email.com', '86999990017', 9),
('Amanda Rodrigues', 'amanda.rodrigues@email.com', '86999990018', 9),
('Rodrigo Freitas', 'rodrigo.freitas@email.com', '86999990019', 10),
('Isabela Nunes', 'isabela.nunes@email.com', '86999990020', 10);

INSERT INTO Professor (nome, email, telefone, formacao) VALUES 
('Alan Turing da Silva', 'alan.turing@uespi.br', '86988881111', 'Doutorado em Ciência da Computação'),
('Ada Lovelace de Sousa', 'ada.lovelace@uespi.br', '86988882222', 'Mestrado em Engenharia de Software'),
('Tim Berners-Lee Costa', 'tim.bl@uespi.br', '86988883333', 'Doutorado em Sistemas de Informação'),
('Grace Hopper Alves', 'grace.hopper@uespi.br', '86988884444', 'Especialização em Banco de Dados'),
('Linus Torvalds Lima', 'linus.torvalds@uespi.br', '86988885555', 'Mestrado em Redes de Computadores'),
('Margaret Hamilton Dias', 'margaret.h@uespi.br', '86988886666', 'Doutorado em Inteligência Artificial'),
('Dennis Ritchie Gomes', 'dennis.ritchie@uespi.br', '86988887777', 'Mestrado em Arquitetura de Sistemas');

INSERT INTO Disciplina (nome, curso_relacionado, bloco_relacionado) VALUES 
('Lógica de Programação', 'Sistemas para Internet', '1'),
('Cálculo Diferencial e Integral I', 'Ciência da Computação', '1'),
('Design de Interfaces Web (UI/UX)', 'Sistemas para Internet', '2'),
('Matemática Discreta', 'Ciência da Computação', '2'),
('Programação Orientada a Objetos', 'Sistemas para Internet', '3'),
('Estruturas de Dados', 'Ciência da Computação', '3'),
('Banco de Dados I', 'Ciência da Computação', '4'),
('Desenvolvimento Web Backend', 'Sistemas para Internet', '4'),
('Redes de Computadores', 'Sistemas para Internet', '5'),
('Engenharia de Software', 'Ciência da Computação', '5'),
('Inteligência Artificial', 'Ciência da Computação', '6'),
('Segurança da Informação', 'Sistemas para Internet', '6'),
('Sistemas Operacionais', 'Ciência da Computação', '7'),
('Computação em Nuvem', 'Sistemas para Internet', '7'),
('Compiladores', 'Ciência da Computação', '8');