package com.task.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.DTO.BoardsCreateDTO;
import com.task.DTO.BoardsRessponseDTO;
import com.task.DTO.BoardsUpdateDTO;
import com.task.DTO.UserCreateDTO;
import com.task.DTO.UserResponseDTO;
import com.task.model.Boards;
import com.task.model.User;
import com.task.service.BoardService;
import com.task.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/board")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "Board Controller", description = "Quản lý các bảng")
public class BoardRest {
	private final UserService userService;
	private final BoardService boardService;

	@GetMapping
	@Operation(summary = "Lấy danh sách boards")
	public ResponseEntity<List<BoardsRessponseDTO>> getAllBoard() {
		List<Boards> boards = boardService.findAll();
		List<BoardsRessponseDTO> result = boards.stream()
			    .map(board -> mapToResponseDTO(board))
			    .toList();
		return ResponseEntity.ok(result);
	}
	

	@GetMapping("/{id}")
	@Operation(summary = "Lấy chi tiết boards")
	public ResponseEntity<BoardsRessponseDTO> getDetailBoard(@PathVariable UUID id) {
		return boardService.findById(id).map(board -> ResponseEntity.ok(mapToResponseDTO(board))).orElse(ResponseEntity.notFound().build());
	}


	@PostMapping
	@Operation(summary = "Tạo boards")
	public ResponseEntity<BoardsRessponseDTO> create(@Valid @RequestBody BoardsCreateDTO dto) {
	    Boards board = mapToEntity(dto);
	    Boards saved = boardService.save(board);
	    return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponseDTO(saved));
	}


	@PutMapping("/{id}")
	@Operation(summary = "Chỉnh sửa chi tiết boards")
	public ResponseEntity<BoardsRessponseDTO> update(@PathVariable UUID id, @Valid @RequestBody BoardsUpdateDTO dto) {
	    Optional<Boards> optionalBoard = boardService.findById(id);
	    if (optionalBoard.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }
	    Boards board = optionalBoard.get();
	    board.setName(dto.getName());
	    // nếu bạn muốn cho phép đổi owner:
	     board.setOwner(userService.findById(dto.getOwnerId()).orElseThrow());
	    Boards updated = boardService.save(board);
	    return ResponseEntity.ok(mapToResponseDTO(updated));
	}


	@DeleteMapping("/{id}")
	@Operation(summary = "Xóa boards")
	public ResponseEntity<?> delete(@PathVariable UUID id) {
		boardService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	// ---------- Mapping methods ----------
	private Boards mapToEntity(BoardsCreateDTO dto) {
		// Tìm user từ UUID
		User owner = userService.findById(dto.getOwnerId()).orElseThrow(() -> new RuntimeException("Owner not found"));
		Boards boards = new Boards();
		boards.setName(dto.getName());
		boards.setOwner(owner); // Gán User vào, không phải UUID
		return boards;
	}

	private BoardsRessponseDTO mapToResponseDTO(Boards boards) {
		BoardsRessponseDTO dto = new BoardsRessponseDTO();
		dto.setId(boards.getId());
		dto.setName(boards.getName());
		dto.setOwnerId(boards.getOwner().getId());
		return dto;
	}
}
