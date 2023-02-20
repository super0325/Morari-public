package com.campingmapping.team4.spring.t401member.controller.api;
// package com.campingmapping.team4.spring.t4_01Member.controller.api;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;

// import
// com.campingmapping.team4.spring.t4_01Member.model.dao.repository.ProfilesRepository;
// import com.campingmapping.team4.spring.t4_01Member.model.entity.Profiles;

// @Controller
// public class RestCrudController {

// @Autowired
// private ProfilesRepository pService;

// @PostMapping("/profiles1")
// @ResponseBody
// public Profiles processInsertAction2(@RequestBody Profiles p) {
// Profiles up = pService.save(p);
// return up;
// }

// @PostMapping("/profiles")
// @ResponseBody
// public String processInsertAction(@RequestParam("userName") String userName,
// @RequestParam("userAddress") String userAddress,
// @RequestParam("userPhone") String userPhone) {
// Profiles p1 = new Profiles();
// p1.setName(userName);
// p1.setAddress(userAddress);
// p1.setPhone(userPhone);
// pService.save(p1);
// return "Insert OK";
// }

// @GetMapping("/profiles/{pid}")
// @ResponseBody
// public String processQueryByIdAction(@PathVariable("pid") Integer pid) {
// Profiles resultBean = pService.findById(pid).get();

// if (resultBean != null) {
// return "Result:" + resultBean.getId() + " " + resultBean.getName() + " "
// + resultBean.getAddress() + " "
// + resultBean.getPhone();
// }

// return "no Result";
// }

// @PutMapping("/profiles/{pid}")
// @ResponseBody
// public String processUpdateAction(@PathVariable("pid") int pid,
// @RequestParam("userName") String userName,
// @RequestParam("userAddress") String userAddress, @RequestParam("userPhone")
// String userPhone) {
// Profiles p2 = new Profiles(pid, userName, userAddress, userPhone);
// pService.save(p2);
// return "Update OK";
// }

// @DeleteMapping("/profiles/{pid}")
// @ResponseBody
// public String processDelectAction(@PathVariable("pid") int pid,
// @RequestParam("userName") String userName,
// @RequestParam("userAddress") String userAddress, @RequestParam("userPhone")
// String userPhone) {
// Profiles p2 = new Profiles(pid, userName, userAddress, userPhone);
// pService.delete(p2);
// return "Update OK";
// }

// }
